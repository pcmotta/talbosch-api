package br.com.attomtech.talbosch.api.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.dto.pesquisa.EstoquePesquisaDTO;
import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.jasper.JasperUtils;
import br.com.attomtech.talbosch.api.log.EstoqueLog;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.Pedido;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.StatusEstoque;
import br.com.attomtech.talbosch.api.model.enums.TipoEstoque;
import br.com.attomtech.talbosch.api.reports.EstoqueTecnicoReport;
import br.com.attomtech.talbosch.api.repository.EstoqueRepository;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoriaDto;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class EstoqueService extends AuditoriaService<Estoque> implements NegocioServiceAuditoriaDto<Estoque, EstoqueFilter, EstoquePesquisaDTO, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( EstoqueService.class );
    
    private EstoqueRepository repository;
    private EstoqueLog        log;
    private UsuarioService    usuarioService;
    private JasperUtils       jasper;
    private MensagemService   mensagemService;
    
    @Autowired
    public EstoqueService( EstoqueRepository repository, UsuarioService usuarioService, EstoqueLog log, JasperUtils jasper, MensagemService mensagemService )
    {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.log = log;
        this.jasper = jasper;
        this.mensagemService = mensagemService;
    }

    @Override
    public Page<EstoquePesquisaDTO> pesquisar( EstoqueFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando -> {}", filtro );
        
        Page<EstoquePesquisaDTO> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    public List<Estoque> buscarPorPedido( Pedido pedido )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Pedido-> {}", pedido );
        
        List<Estoque> estoques = repository.buscarPorPedido( pedido );
        
        return estoques;
    }

    @Override
    public Estoque cadastrar( Estoque estoque, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando -> {}", estoque );
        
        atualizarAuditoriaInclusao( estoque, login );
        tratarRelacionamentos( estoque, login );
        
        if( estoque.getAgendadoPara( ) != null )
            estoque.setAgendadoPor( usuarioService.buscarPorLogin( login ) );
        
        estoque = salvar( estoque );
        log.logar( AcaoLog.CADASTRO, estoque.getCodigo( ), estoque );
        
        notificar( estoque, null, login );
        
        return estoque;
    }

    @Transactional(rollbackFor = NegocioException.class)
    @Cacheable(value = "estoqueItem", key = "#estoque.codigo")
    @Override
    public Estoque atualizar( Estoque estoque, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando -> {}", estoque );
        
        Estoque estoqueSalvo = buscarPorCodigo( estoque.getCodigo( ) ).clone( );
        
        estoque.setAuditoria( estoqueSalvo.getAuditoria( ) );
        atualizarAuditoriaAlteracao( estoque, login );
        tratarRelacionamentos( estoque, login );
        
        if( estoque.getAgendadoPara( ) != null && 
                ( estoqueSalvo.getAgendadoPara( ) == null || !estoque.getAgendadoPara( ).isEqual( estoqueSalvo.getAgendadoPara( ) ) ) )
            estoque.setAgendadoPor( usuarioService.buscarPorLogin( login ) );
        
        if( estoque.getAgendadoPara( ) == null )
            estoque.setAgendadoPor( null );
        
        if( estoque.getPedido( ) == null && estoqueSalvo.getPedido( ) != null)
            estoque.setPedido( estoqueSalvo.getPedido( ) );
        
        try
        {
            if( estoqueSalvo.getStatus( ) != StatusEstoque.NAOBUSCADA && estoque.getStatus( ) == StatusEstoque.NAOBUSCADA )
            {
                Estoque novoEstoque = new Estoque( );
                
                novoEstoque.setStatus( StatusEstoque.EMESTOQUE );
                novoEstoque.setPeca( estoque.getPeca( ) );
                novoEstoque.setTipo( estoque.getTipo( ) );
                novoEstoque.setPedido( estoque.getPedido( ) );
                
                cadastrar( novoEstoque, login );
            }
            
            estoque = salvar( estoque );
            log.logar( AcaoLog.ALTERACAO, estoqueSalvo.getCodigo( ), estoqueSalvo, estoque );
            
            notificar( estoqueSalvo, estoque, login );
            
            return estoque;
        }
        catch( Exception e )
        {
            LOGGER.error( e.getMessage( ), e );
            throw new NegocioException( e.getMessage( ), e );
        }
    }

    @CacheEvict(value = "estoqueItem", key = "#codigo")
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo -> {}", codigo );
        
        Estoque estoque = buscarPorCodigo( codigo ).clone( );
        atualizarAuditoriaAlteracao( estoque, login );
        
        repository.deleteById( codigo );
        
        log.logar( AcaoLog.EXCLUSAO, codigo, estoque );
        notificar( estoque, null, login );
    }

    @Cacheable(value = "estoqueItem", key = "#codigo")
    @Override
    public Estoque buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código -> {}", codigo );
        
        Optional<Estoque> estoque = repository.findById( codigo );
        
        return estoque.orElseThrow( ( ) -> new NegocioException( "Estoque não encontrado" ) );
    }

    @Override
    public Estoque salvar( Estoque estoque )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando -> {}", estoque );
        
        return repository.save( estoque );
    }
    
    @Cacheable(value = "tiposEstoque")
    public LabelValue[] buscarTipos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando tipos" );
        
        TipoEstoque[] values = TipoEstoque.values( );
        LabelValue[] labelValue = new LabelValue[values.length];
        
        IntStream.range( 0, values.length ).forEach( index -> labelValue[index] = new LabelValue( values[index], values[index].getDescricao( ) ) );
        
        return labelValue;
    }
    
    @Cacheable(value = "statusEstoque")
    public List<LabelValue> buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando status" );
        
        List<StatusEstoque> status = Arrays.asList( StatusEstoque.values( ) );
        status.sort( (st1, st2) -> st1.getOrdem( ) > st2.getOrdem( ) ? 1 : -1 );
        
        List<LabelValue> labelValue = new ArrayList<LabelValue>( );
        
        status.forEach( (st) -> labelValue.add( new LabelValue( st, st.getDescricao( ) ) ) );
        
        return labelValue;
    }
    
    public Long buscarPecasAChegar( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscar peças a caminho" );
        
        return repository.pecasPorStatus( StatusEstoque.ACHEGAR );
    }
    
    public Long buscarSemClienteEOrdemDeServico( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscar peças sem Cliente e Ordem de Serviço" );
        
        return repository.buscarSemClienteEOrdemDeServico( );
    }
    
    public Long buscarPecasNaoAgendadas( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscar peças não agendadas" );
        
        return repository.buscarNaoAgendadas( );
    }
    
    public byte[] relatorioTecnico( EstoqueTecnicoReport filtro )
    {
        try
        {
            Map<String, Object> parameters = new HashMap<String, Object>( );
            
            parameters.put( "DATA",           filtro.getData( )                                                        );
            parameters.put( "ISGARANTIA",     filtro.isTodos( ) || filtro.isGarantia( )                                );
            parameters.put( "ISFORAGARANTIA", filtro.isTodos( ) || filtro.isForaGarantia( )                            );
            parameters.put( "ISBUFFER",       filtro.isTodos( ) || filtro.isBuffer( )                                  );
            parameters.put( "TECNICO",        filtro.getTecnico( ) == null ? "TODOS" : filtro.getTecnico( ).getNome( ) );
            
            List<Estoque> estoques = repository.buscarEstoqueTecnico( filtro );
            estoques.sort( (e1, e2) -> e1.getTecnico( ).getNome( ).compareTo( e2.getTecnico( ).getNome( ) ) );
            
            List<Estoque> estoquesReport = new ArrayList<Estoque>( );
            
            Estoque oldEstoque = null;
            for( Estoque estoque : estoques )
            {
                if( oldEstoque == null )
                    oldEstoque = estoque;
                
                if( !oldEstoque.getTecnico( ).equals( estoque.getTecnico( ) ) )
                    estoquesReport.add( new Estoque( ) );
                
                estoquesReport.add( estoque );
                
                oldEstoque = estoque;
            }
            
            return jasper.gerarPdf( "pecas_tecnico.jasper", estoquesReport, parameters );
        }
        catch( Exception e )
        {
            LOGGER.error( e.getMessage( ), e );
            throw new NegocioException( "Erro ao gerar relatório de peças por técnico", e );
        }
    }
    
    public void notificar( Estoque estoqueOld, Estoque estoqueNew, String login )
    {
        List<Usuario> usuarios = usuarioService.buscarUsuariosParaNotificarEstoque( )
                .stream( ).filter( u -> !u.getLogin( ).equals( login ) ).collect( Collectors.toList( ) );
        
        String logMessage = log.log( estoqueOld, estoqueNew, estoqueNew != null );
        
        if( StringUtils.hasText( logMessage ) )
        {
            String logText = "Código: " + estoqueOld.getCodigo( ) + "\n" +
                             "Usuário: " + usuarioService.buscarPorLogin( login ).getNome( ) + "\n\n" + logMessage;
            usuarios.forEach( usuario -> mensagemService.notificarEstoque( usuario, logText ) );
        }
    }

    private void tratarRelacionamentos( Estoque estoque, String login )
    {
        if( estoque.getTelefones( ) != null )
            estoque.getTelefones( ).forEach( telefone -> telefone.setEstoque( estoque ) );
        
        Usuario usuario = usuarioService.buscarPorLogin( login );
        
        if( estoque.getObservacoes( ) != null )
            estoque.getObservacoes( ).forEach( observacao -> {
                observacao.setEstoque( estoque );
                
                if( observacao.getUsuario( ) == null )
                    observacao.setUsuario( usuario );
            });
    }
}
