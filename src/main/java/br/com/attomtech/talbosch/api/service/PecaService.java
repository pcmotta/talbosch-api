package br.com.attomtech.talbosch.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Peca;
import br.com.attomtech.talbosch.api.repository.PecaRepository;
import br.com.attomtech.talbosch.api.repository.filter.PecaFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;

@Service
public class PecaService extends AuditoriaService<Peca> implements NegocioServiceAuditoria<Peca, PecaFilter, String>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( PecaService.class );
    
    private PecaRepository repository;
    
    @Autowired
    public PecaService( PecaRepository repository )
    {
        this.repository = repository;
    }
    
    @Override
    public Page<Peca> pesquisar( PecaFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando > {}", filtro );
        
        Page<Peca> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Override
    public Peca cadastrar( Peca peca, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando Peça > {}", peca );
        
        if( repository.countByCodigo( peca.getCodigo( ) ) > 0 )
            throw new NegocioException( "Peça já existe" );
        
        atualizarAuditoriaInclusao( peca, login );
        tratarModelos( peca );
        
        return salvar( peca );
    }
    
    @Override
    public Peca atualizar( Peca peca, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando Peça > {}", peca );
        
        Peca pecaSalva = buscarPorCodigo( peca.getCodigo( ) );
        
        if( pecaSalva.isInativo( ) )
            throw new NegocioException( "Impossível alterar dados de Peça Inativa" );
        
        atualizarAuditoriaAlteracao( pecaSalva, login );
        peca.setAuditoria( pecaSalva.getAuditoria( ) );
        tratarModelos( peca );
        
        return salvar( peca );
    }
    
    @Override
    public void excluir( String codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Peca peca = buscarPorCodigo( codigo );
        
        atualizarAuditoriaAlteracao( peca, login );
        peca.setAtivo( false );
        
        salvar( peca );
    }
    
    @Override
    public Peca salvar( Peca peca )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando Peça > {}", peca );
        
        Peca pecaSalva = repository.save( peca );
        
        return pecaSalva;
    }
    
    @Override
    public Peca buscarPorCodigo( String codigo )
    {
        Optional<Peca> pecaOpt = repository.findById( codigo );
        
        return pecaOpt.orElseThrow( ( ) -> new NegocioException( "Peça não encontrada" ) );
    }
    
    private void tratarModelos( Peca peca )
    {
        peca.getModelos( ).forEach( modelo -> modelo.setPeca( peca ) );
    }

}
