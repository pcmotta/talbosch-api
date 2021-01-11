package br.com.attomtech.talbosch.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Tecnico;
import br.com.attomtech.talbosch.api.repository.TecnicoRepository;
import br.com.attomtech.talbosch.api.repository.filter.TecnicoFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioService;

@Service
public class TecnicoService implements NegocioService<Tecnico, TecnicoFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TecnicoService.class );

    private TecnicoRepository repository;
    
    @Autowired
    public TecnicoService( TecnicoRepository repository )
    {
        this.repository = repository;
    }
    
    @Override
    public Page<Tecnico> pesquisar( TecnicoFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Tecnico> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Override
    public Tecnico cadastrar( Tecnico tecnico )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", tecnico );
        
        return salvar( tecnico );
    }
    
    @Override
    public Tecnico atualizar( Tecnico tecnico )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", tecnico );
        
        if( tecnico.getCodigo( ) == null )
            throw new NegocioException( "Usuário não informado" );
        
        Tecnico tecnicoSalvo = buscarPorCodigo( tecnico.getCodigo( ) );
        
        tecnico.setAtivo( tecnicoSalvo.isAtivo( ) );
        
        return salvar( tecnico );
    }
    
    @Override
    public void excluir( Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Tecnico tecnico = buscarPorCodigo( codigo );
        
        tecnico.setAtivo( false );
        
        salvar( tecnico );
    }

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
