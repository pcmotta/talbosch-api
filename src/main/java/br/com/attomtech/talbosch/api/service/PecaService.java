package br.com.attomtech.talbosch.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.dto.PecaDTO;
import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.log.PecaLog;
import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.Aparelho;
import br.com.attomtech.talbosch.api.model.enums.Fabricante;
import br.com.attomtech.talbosch.api.repository.PecaRepository;
import br.com.attomtech.talbosch.api.repository.filter.PecaFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class PecaService extends AuditoriaService<Peca> implements NegocioServiceAuditoria<Peca, PecaFilter, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( PecaService.class );
    
    private PecaRepository repository;
    private PecaLog log;
    
    @Autowired
    public PecaService( PecaRepository repository, PecaLog log )
    {
        this.repository = repository;
        this.log = log;
    }
    
    @Override
    public Page<Peca> pesquisar( PecaFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Peca> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Cacheable(value = "pecasTodas")
    public List<PecaDTO> buscarPecas( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando peças" );
        
        List<Peca> pecas = repository.findAll( );
        
        return pecas.stream( ).map( peca -> new PecaDTO( peca ) ).collect( Collectors.toList( ) );
    }
    
    @Override
    public Peca cadastrar( Peca peca, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando Peça > {}", peca );
        
        if( repository.countByCodigo( peca.getCodigo( ) ) > 0 )
            throw new NegocioException( "Peça já existe" );
        
        atualizarAuditoriaInclusao( peca, login );
        
        peca = salvar( peca );
        log.logar( AcaoLog.CADASTRO, peca.getCodigo( ), peca );
        
        return peca;
    }
    
    @Caching(evict = { @CacheEvict(value = "peca", key = "#peca.codigo"), @CacheEvict(value = "pecasTodas", allEntries = true) })
    @Override
    public Peca atualizar( Peca peca, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando Peça > {}", peca );
        
        Peca pecaSalva = buscarPorCodigo( peca.getCodigo( ) ).clone( );
        
        if( pecaSalva.isInativo( ) )
            throw new NegocioException( "Impossível alterar dados de Peça Inativa" );
        
        atualizarAuditoriaAlteracao( pecaSalva, login );
        peca.setAuditoria( pecaSalva.getAuditoria( ) );
        
        peca = salvar( peca );
        log.logar( AcaoLog.ALTERACAO, pecaSalva.getCodigo( ), pecaSalva, peca );
        
        return peca;
    }
    
    @Caching(evict = { @CacheEvict(value = "peca", key = "#codigo"), @CacheEvict(value = "pecasTodas", allEntries = true) })
    @Override
    public void excluir( String codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Peca peca = buscarPorCodigo( codigo );
        
        atualizarAuditoriaAlteracao( peca, login );
        peca.setAtivo( false );
        
        salvar( peca );
        log.logar( AcaoLog.EXCLUSAO, codigo, peca );
    }
    
    @Override
    public Peca salvar( Peca peca )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando Peça > {}", peca );
        
        Peca pecaSalva = repository.save( peca );
        
        return pecaSalva;
    }
    
    @Cacheable(value = "peca", key = "#codigo")
    @Override
    public Peca buscarPorCodigo( String codigo )
    {
        Optional<Peca> pecaOpt = repository.findById( codigo );
        
        return pecaOpt.orElseThrow( ( ) -> new NegocioException( "Peça não encontrada" ) );
    }
    
    @Cacheable(value = "aparelhos")
    public LabelValue[] buscarAparelhos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Aparelhos" );
        
        Aparelho[] aparelhos = Aparelho.values( );
        LabelValue[] values = new LabelValue[aparelhos.length];
        
        IntStream.range( 0, aparelhos.length ).forEach( index -> 
            values[index] = new LabelValue( aparelhos[index].toString( ), aparelhos[index].getDescricao( ) ) );
        
        return values;
    }
    
    @Cacheable(value = "fabricantes")
    public LabelValue[] buscarFabricantes( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Fabricantes" );
        
        Fabricante[] fabricantes = Fabricante.values( );
        LabelValue[] values = new LabelValue[fabricantes.length];
        
        IntStream.range( 0, fabricantes.length ).forEach( index -> 
            values[index] = new LabelValue( fabricantes[index].toString( ), fabricantes[index].getNome( ) ) );
        
        return values;
    }
}
