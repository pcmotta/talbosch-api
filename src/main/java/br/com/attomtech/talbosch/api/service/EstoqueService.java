package br.com.attomtech.talbosch.api.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Estoque;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.EstoqueRepository;
import br.com.attomtech.talbosch.api.repository.filter.EstoqueFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;

@Service
public class EstoqueService extends AuditoriaService<Estoque> implements NegocioServiceAuditoria<Estoque, EstoqueFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( EstoqueService.class );
    
    private EstoqueRepository repository;
    private UsuarioService usuarioService;
    
    @Autowired
    public EstoqueService( EstoqueRepository repository, UsuarioService usuarioService )
    {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Override
    public Page<Estoque> pesquisar( EstoqueFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando -> {}", filtro );
        
        Page<Estoque> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
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
        
        return salvar( estoque );
    }

    @Override
    public Estoque atualizar( Estoque estoque, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando -> {}", estoque );
        
        Estoque estoqueSalvo = buscarPorCodigo( estoque.getCodigo( ) );
        
        estoque.setAuditoria( estoqueSalvo.getAuditoria( ) );
        atualizarAuditoriaAlteracao( estoque, login );
        tratarRelacionamentos( estoque, login );
        
        if( estoque.getAgendadoPara( ) != null && !estoque.getAgendadoPara( ).isEqual( estoqueSalvo.getAgendadoPara( ) ) )
            estoque.setAgendadoPor( usuarioService.buscarPorLogin( login ) );
        
        return salvar( estoque );
    }

    public void excluir( Long codigo )
    {
        excluir( codigo, null );
    }
    
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Excluíndo -> {}", codigo );
        
        repository.deleteById( codigo );
    }

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
