package br.com.attomtech.talbosch.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import br.com.attomtech.talbosch.api.dto.OrdemServicoRelatorioDTO;
import br.com.attomtech.talbosch.api.dto.pesquisa.OrdemServicoPesquisaDTO;
import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.jasper.JasperUtils;
import br.com.attomtech.talbosch.api.log.OrdemServicoLog;
import br.com.attomtech.talbosch.api.model.Cliente;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.OrdemServico;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.StatusOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdem;
import br.com.attomtech.talbosch.api.model.enums.TipoOrdemValor;
import br.com.attomtech.talbosch.api.repository.EstoqueRepository;
import br.com.attomtech.talbosch.api.repository.OrdemServicoRepository;
import br.com.attomtech.talbosch.api.repository.filter.OrdemServicoFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;
import net.sf.jasperreports.engine.JRException;

@Service
public class OrdemServicoService extends AuditoriaService<OrdemServico> implements NegocioServiceAuditoria<OrdemServico, OrdemServicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( OrdemServicoService.class );

    private OrdemServicoRepository repository;
    private OrdemServicoLog        log;
    private EstoqueRepository      estoqueRepository;
    private UsuarioService         usuarioService;
    private EstoqueService         estoqueService;
    private JasperUtils            jasper;
    
    @Autowired
    public OrdemServicoService( OrdemServicoRepository repository, EstoqueRepository estoqueRepository, UsuarioService usuarioService, 
            OrdemServicoLog log, EstoqueService estoqueService, JasperUtils jasper )
    {
        this.repository = repository;
        this.estoqueRepository = estoqueRepository;
        this.usuarioService = usuarioService;
        this.log = log;
        this.estoqueService = estoqueService;
        this.jasper =  jasper;
    }

    @Override
    public Page<OrdemServicoPesquisaDTO> pesquisar( OrdemServicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        return repository.pesquisar( filtro, pageable );
    }

    @Cacheable(value = "ordemServico", key = "#codigo")
    @Override
    public OrdemServico buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Optional<OrdemServico> ordem = repository.findById( codigo );
        
        OrdemServico ordemServico = ordem.orElseThrow( ( ) -> new NegocioException( "Ordem de Serviço não encontrada" ) );
        
        List<Estoque> pecas = estoqueRepository.buscarPorOrdem( ordemServico.getNumero( ) );
        ordemServico.setPecas( pecas );
        
        return ordemServico;
    }

    @Override
    public OrdemServico salvar( OrdemServico ordem )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", ordem );
        
        return repository.save( ordem );
    }

    @Override
    public OrdemServico cadastrar( OrdemServico ordem, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", ordem );
        
        atualizarAuditoriaInclusao( ordem, login );
        tratarRelacionamentos( ordem, login );
        
        ordem = salvar( ordem );
        log.logar( AcaoLog.CADASTRO, ordem.getNumero( ), ordem );
        
        return ordem;
    }
    
    private void tratarRelacionamentos( OrdemServico ordem, String login )
    {
        ordem.getEndereco( ).setOrdem( ordem );
        ordem.getProduto( ).setOrdem( ordem );
        
        if( ordem.temValor( ) )
            ordem.getValores( ).forEach( valor -> valor.setOrdem( ordem ) );
        
        if( ordem.temAtendimento( ) )
            ordem.getAtendimentos( ).forEach( atendimento -> atendimento.setOrdem( ordem ) );
        
        if( ordem.temAndamento( ) )
            ordem.getAndamentos( ).forEach( andamento -> {
                andamento.setOrdem( ordem );
                
                if( andamento.getUsuario( ) == null )
                    andamento.setUsuario( usuarioService.buscarPorLogin( login ) );
            });
    }
    
    @CacheEvict(value = "ordemServico", key = "#ordem.numero")
    @Override
    @Transactional(rollbackOn = NegocioException.class)
    public OrdemServico atualizar( OrdemServico ordem, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", ordem );
        
        OrdemServico ordemSalva = buscarPorCodigo( ordem.getNumero( ) ).clone( );
        
        ordem.setAuditoria( ordemSalva.getAuditoria( ) );
        atualizarAuditoriaAlteracao( ordem, login );
        tratarRelacionamentos( ordem, login );
        
        try
        {
            List<Estoque> pecas = ordem.getPecas( );
            
            ordem = salvar( ordem );
            log.logar( AcaoLog.ALTERACAO, ordemSalva.getNumero( ), ordemSalva, ordem );

            LocalDate dataAtendimento = ordem.getDataAtendimento( );
            Usuario usuario = usuarioService.buscarPorLogin( login );
            pecas.forEach( p -> {
                Estoque peca = estoqueService.buscarPorCodigo( p.getCodigo( ) ).clone( );
                
                if( p.getAgendadoPara( ) == null ) 
                {
                    peca.setAgendadoPara( null );
                    peca.setAgendadoPor( null );
                }
                else
                {
                    if( !p.getAgendadoPara( ).equals( dataAtendimento ) )
                        peca.setAgendadoPor( usuario );
                    
                    peca.setAgendadoPara( p.getAgendadoPara( ) );
                }
                
                estoqueService.atualizar( peca, login );
            });
        }
        catch( Exception e )
        {
            LOGGER.error( e.getMessage( ), e );
            throw new NegocioException( "Erro ao atualizar Ordem de serviço", e );
        }
        
        
        return ordem;
    }

    @CacheEvict(value = "ordemServico", key = "#numero")
    @Override
    public void excluir( Long numero, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo > {}", numero );
        
        Optional<List<Estoque>> estoques = estoqueRepository.findByOrdemServicoNumero( numero );
        
        if( estoques.isPresent( ) )
            throw new NegocioException( "Não é possível excluir, pois existem peças no estoque associadas a essa ordem de serviço" );
        
        OrdemServico ordem = buscarPorCodigo( numero ).clone( );
        atualizarAuditoriaAlteracao( ordem, login );
        
        repository.deleteById( numero );
        log.logar( AcaoLog.EXCLUSAO, numero, ordem );
    }
    
    public List<OrdemServico> buscarPorCliente( Cliente cliente )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Cliente > {}", cliente );
        
        List<OrdemServico> ordens = repository.findByCliente( cliente );
        
        return ordens;
    }
    
    @Cacheable(value = "statusOrdem")
    public List<LabelValue> buscarStatus( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Status" );

        List<StatusOrdem> status = Arrays.asList( StatusOrdem.values( ) );
        status.sort( (st1, st2) -> st1.getOrdem( ) > st2.getOrdem( ) ? 1 : -1 );
        
        List<LabelValue> labelValue = new ArrayList<LabelValue>( );
        
        status.forEach( (st) -> labelValue.add( new LabelValue( st, st.getDescricao( ) ) ) );
        
        return labelValue;
    }
    
    @Cacheable(value = "tiposOrdem")
    public LabelValue[] buscarTipos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos" );

        TipoOrdem[]  tipos      = TipoOrdem.values( );
        LabelValue[] labelValue = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
                labelValue[index] = new LabelValue( tipos[index], tipos[index].getDescricao( ) ) );
        
        return labelValue;
    }
    
    @Cacheable(value = "tiposValor")
    public LabelValue[] buscarTiposValor( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Tipos Valor" );

        TipoOrdemValor[] tipos      = TipoOrdemValor.values( );
        LabelValue[]     labelValue = new LabelValue[tipos.length];
        
        IntStream.range( 0, tipos.length ).forEach( index ->
                labelValue[index] = new LabelValue( tipos[index], tipos[index].getDescricao( ) ) );
        
        return labelValue;
    }
    
    public byte[] gerarRelatorio( Long numero )
    {
        OrdemServico ordem = buscarPorCodigo( numero );
        
        try
        {
            Map<String, Object> parameters = new HashMap<String, Object>( );
            parameters.put( "numero", numero );
            
            return jasper.gerarPdf( "ordem_servico.jasper", new OrdemServicoRelatorioDTO( ordem ), parameters );
        }
        catch( JRException e )
        {
            LOGGER.error( e.getMessage( ), e );
            throw new NegocioException( "Erro ao gerar pdf de ordem de serviço", e );
        }
    }
    
    public Long buscarAbertasSemAgendamento( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ordens Abertas sem Agendamento" );
        
        return repository.ordensAbertasSemAtendimento( );
    }
    
    public Long buscarAguardandoPeca( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ordens Aguardando Peça" );
        
        return repository.aguardandoPeca( );
    }

    public Long buscarAtendidasNaoFinalizadas( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Ordens Atendidas Não Finalizadas" );
        
        return repository.atendidasNaoFinalizadas( LocalDate.now( ) );
    }
}
