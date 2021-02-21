package br.com.attomtech.talbosch.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.log.TecnicoLog;
import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.repository.TecnicoRepository;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
public class TecnicoService extends AuditoriaService<Tecnico> implements NegocioServiceAuditoria<Tecnico, TecnicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TecnicoService.class );

    private TecnicoRepository repository;
    private TecnicoLog log;
    
    @Autowired
    public TecnicoService( TecnicoRepository repository, TecnicoLog log )
    {
        this.repository = repository;
        this.log = log;
    }
    
    @Override
    public Page<Tecnico> pesquisar( TecnicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Tecnico> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Cacheable(value = "tecnicosAtivos")
    public List<LabelValue> buscarAtivos( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Técnicos Ativos" );
        
        List<Tecnico> tecnicos = repository.findByAtivoTrue( );
        List<LabelValue> tecnicosAtivos = new ArrayList<LabelValue>( );
        
        tecnicos.forEach( tecnico -> tecnicosAtivos.add( new LabelValue( tecnico.getCodigo( ), tecnico.getNome( ) ) ) );
        
        return tecnicosAtivos;
    }
    
    @Override
    public Tecnico cadastrar( Tecnico tecnico, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", tecnico );
        
        atualizarAuditoriaInclusao( tecnico, login );
        
        tecnico = salvar( tecnico );
        log.logar( AcaoLog.CADASTRO, tecnico.getCodigo( ), tecnico );
        
        return tecnico;
    }
    
    @Caching(evict = { @CacheEvict(value = "tecnico", key = "#tecnico.codigo"), @CacheEvict(value = "tecnicosAtivos", allEntries = true) })
    @Override
    public Tecnico atualizar( Tecnico tecnico, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", tecnico );
        
        if( tecnico.getCodigo( ) == null )
            throw new NegocioException( "Usuário não informado" );
        
        Tecnico tecnicoSalvo = buscarPorCodigo( tecnico.getCodigo( ) ).clone( );
        atualizarAuditoriaAlteracao( tecnicoSalvo, login );
        
        tecnico.setAuditoria( tecnicoSalvo.getAuditoria( ) );
        tecnico.setAtivo( tecnicoSalvo.isAtivo( ) );
        
        tecnico = salvar( tecnico );
        log.logar( AcaoLog.ALTERACAO, tecnicoSalvo.getCodigo( ), tecnicoSalvo, tecnico );
        
        return tecnico;
    }
    
    @Caching(evict = { @CacheEvict(value = "tecnico", key = "#codigo"), @CacheEvict(value = "tecnicosAtivos", allEntries = true) })
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Tecnico tecnico = buscarPorCodigo( codigo );
        atualizarAuditoriaAlteracao( tecnico, login );
        
        tecnico.setAtivo( false );
        
        salvar( tecnico );
        log.logar( AcaoLog.EXCLUSAO, codigo, tecnico );
    }

    @Cacheable(value = "tecnico", key = "#codigo")
    @Override
    public Tecnico buscarPorCodigo( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Código > {}", codigo );
        
        Optional<Tecnico> tecnico = repository.findById( codigo );
        
        return tecnico.orElseThrow( ( ) -> new NegocioException( "Técnico não encontrado" ) );
    }

    @Override
    public Tecnico salvar( Tecnico tecnico )
    {
        return repository.save( tecnico );
    }
}
