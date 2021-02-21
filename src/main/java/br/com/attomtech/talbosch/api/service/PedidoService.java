package br.com.attomtech.talbosch.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.filter.PedidoFilter;
import br.com.attomtech.talbosch.api.log.PedidoLog;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.StatusPedido;
import br.com.attomtech.talbosch.api.repository.PedidoRepository;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class PedidoService extends AuditoriaService<Pedido> implements NegocioServiceAuditoria<Pedido, PedidoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( PedidoService.class );
    
    private PedidoRepository repository;
    private PedidoLog        log;
    private EstoqueService   estoqueService;
    
    @Autowired
    public PedidoService( PedidoRepository repository, PedidoLog log, EstoqueService estoqueService )
    {
        this.repository     = repository;
        this.log            = log;
        this.estoqueService = estoqueService;
    }

    @Override
    public Page<Pedido> pesquisar( PedidoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Pedido> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }

    @Override
    @Transactional(rollbackOn = NegocioException.class)
    public Pedido cadastrar( Pedido pedido, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", pedido );
        
        try
        {
            atualizarAuditoriaInclusao( pedido, login );
            Pedido pedidoSalvo = salvar( pedido );
            
            pedido.getPecas( ).forEach( peca -> {
                atualizarPeca( pedidoSalvo, peca );
                
                if( peca.getCodigo( ) != null )
                    estoqueService.atualizar( peca, login );
                else
                    estoqueService.cadastrar( peca, login );
            });
            
            pedidoSalvo.getPecas( ).clear( );
            
            log.logar( AcaoLog.CADASTRO, pedidoSalvo.getCodigo( ), pedidoSalvo );
            return pedidoSalvo;
        }
        catch( Exception e )
        {
            throw new NegocioException( "Erro ao cadastrar pedido", e );
        }
    }

    
    @CacheEvict(value = "pedido", key = "#pedido.codigo")
    @Override
    @Transactional(rollbackOn = NegocioException.class)
    public Pedido atualizar( Pedido pedido, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", pedido );
        
        try
        {
            Pedido pedidoSalvo = buscarPorCodigo( pedido.getCodigo( ) ).clone( );
            List<Estoque> pecasAtuais = estoqueService.buscarPorPedido( pedido );

            boolean novoPedidoCriado = pedidoSalvo.getStatus( ) == StatusPedido.AGUARDANDO && pedido.getStatus( ) == StatusPedido.CONCLUIDO &&
                        pedido.getPecas( ).stream( ).filter( p -> p.getStatus( ) == StatusEstoque.ACHEGAR ).count( ) > 0;
            
            if( novoPedidoCriado )
            {
                List<Estoque> pecasNovas = pedido.getPecas( );
                List<Estoque> pecasNovoPedido = pecasNovas.stream( ).filter( p -> p.getStatus( ) == StatusEstoque.ACHEGAR ).collect( Collectors.toList( ) );
                
                Pedido novoPedido = pedido.clone( );
                novoPedido.setCodigo( null );
                novoPedido.setPecas( pecasNovoPedido );
                novoPedido.setNotaFiscal( null );
                novoPedido.setEmissaoNotaFiscal( null );
                novoPedido.setChegadaNotaFiscal( null );
                novoPedido.setStatus( StatusPedido.AGUARDANDO );
                
                cadastrar( novoPedido, login );
            }
            
            Pedido pedidoCopy = pedido.clone( );
            pecasAtuais.forEach( peca -> {
                int indexOf = pedidoCopy.getPecas( ).indexOf( peca );
                if( indexOf != -1 )
                {
                    Estoque pecaNova = pedidoCopy.getPecas( ).get( indexOf );
                    
                    if( !novoPedidoCriado || pecaNova.getStatus( ) != StatusEstoque.ACHEGAR )
                    {
                        atualizarPeca( pedidoCopy, pecaNova );
                        estoqueService.atualizar( pecaNova, login );
                    }
                }
                else
                    estoqueService.excluir( peca.getCodigo( ), login );
            });
            
            pedidoCopy.getPecas( ).stream( ).filter( p -> p.getCodigo( ) == null ).forEach( peca -> {
                if( !novoPedidoCriado || peca.getStatus( ) != StatusEstoque.ACHEGAR )
                {
                    atualizarPeca( pedidoCopy, peca );
                    estoqueService.cadastrar( peca, login );
                }
            });
            
            atualizarAuditoriaAlteracao( pedidoSalvo, login );
            pedido.setAuditoria( pedidoSalvo.getAuditoria( ) );
            
            pedido = salvar( pedido );
            log.logar( AcaoLog.ALTERACAO, pedido.getCodigo( ), pedidoSalvo, pedido );
            
            return pedido;
        }
        catch( Exception e )
        {
            LOGGER.error( e.getMessage( ), e );
            throw new NegocioException( "Erro ao atualizar pedido", e );
        }
    }

    @CacheEvict(value = "pedido", key = "#codigo")
    @Override
    @Transactional(rollbackOn = NegocioException.class)
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", codigo );
        
        try
        {
            Pedido pedido = buscarPorCodigo( codigo );
            List<Estoque> pecas = estoqueService.buscarPorPedido( pedido );
            
            pecas.forEach( peca -> estoqueService.excluir( peca.getCodigo( ), login ) );
            
            atualizarAuditoriaAlteracao( pedido, login );
            repository.delete( pedido );
            
            log.logar( AcaoLog.EXCLUSAO, codigo, pedido );
        }
        catch( Exception e )
        {
            throw new NegocioException( "Erro ao excluir pedido", e );
        }
    }

    @Cacheable(value = "pedido", key = "#codigo")
    @Override
    public Pedido buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Optional<Pedido> pedidoOpt = repository.findById( codigo );
        
        Pedido pedido = pedidoOpt.orElseThrow( () -> new NegocioException( "Pedido não encontrado" ) );
        
        List<Estoque> pecas = estoqueService.buscarPorPedido( pedido );
        pedido.setPecas( pecas );
        
        return pedido;
    }

    @Override
    public Pedido salvar( Pedido pedido )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", pedido );
        
        return repository.save( pedido );
    }
    
    @Cacheable(value = "statusPedido")
    public LabelValue[] buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Status de Pedido" );
        
        StatusPedido[] status = StatusPedido.values( );
        LabelValue[] valores = new LabelValue[status.length];
        
        IntStream.range( 0, status.length ).forEach( index -> valores[index] = new LabelValue( status[index], status[index].getDescricao( ) ) );
        
        return valores;
    }
    
    private void atualizarPeca( Pedido pedido, Estoque peca )
    {
        peca.setPedido( pedido );
    }
}
