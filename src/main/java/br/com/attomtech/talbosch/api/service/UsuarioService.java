package br.com.attomtech.talbosch.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.UsuarioRepository;
import br.com.attomtech.talbosch.api.repository.filter.UsuarioFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;

@Service
public class UsuarioService implements NegocioServiceAuditoria<Usuario, UsuarioFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( UsuarioService.class );
    
    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Page<Usuario> pesquisar( UsuarioFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando usuários" );
        
        Page<Usuario> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    public List<Usuario> buscarUsuarios( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando usuários" );

        List<Usuario> usuarios = repository.findAll( );
        
        return usuarios.stream( ).filter( u -> u.getCodigo( ) > 1 ).collect( Collectors.toList( ) );
    }
    
    @Override
    public Usuario cadastrar( Usuario usuario, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", usuario );
        
        Usuario usuarioLogado = buscarPorLogin( login );
        
        usuario.getAuditoria( ).setIncluidoPor( usuarioLogado );
        usuario.getAuditoria( ).setIncluidoEm( LocalDateTime.now( ) );
        usuario.setSenha( passwordEncoder.encode( usuario.getSenha( ) ) );
        
        return salvar( usuario );
    }
    
    @Override
    public Usuario atualizar( Usuario usuario, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", usuario );
        
        if( usuario.isNovo( ) )
            throw new NegocioException( "Usuário não fornecido" );
        
        Usuario usuarioSalvo = buscarPorCodigo( usuario.getCodigo( ) );
        
        if( StringUtils.hasText( usuario.getSenha( ) ) )
            usuario.setSenha( passwordEncoder.encode( usuario.getSenha( ) ) );
        else
            usuario.setSenha( usuarioSalvo.getSenha( ) );
        
        Usuario usuarioLogado = buscarPorLogin( login );
        usuario.setAuditoria( usuarioSalvo.getAuditoria( ) );
        usuario.getAuditoria( ).setAlteradoPor( usuarioLogado );
        usuario.getAuditoria( ).setAlteradoEm( LocalDateTime.now( ) );
        
        return salvar( usuario ); 
    }
    
    @Override
    public void excluir( Long codigo, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Inativando > {}", codigo );
        
        Usuario usuario       = buscarPorCodigo( codigo );
        Usuario usuarioLogado = buscarPorLogin( login );
        
        usuario.getAuditoria( ).setAlteradoPor( usuarioLogado );
        usuario.getAuditoria( ).setAlteradoEm( LocalDateTime.now( ) );
        usuario.setAtivo( false );
        
        salvar( usuario );
    }
    
    @Override
    public Usuario buscarPorCodigo( Long codigo )
    {
        Optional<Usuario> usuarioOpt = repository.findById( codigo );
        
        return usuarioOpt.orElseThrow( ( ) -> new NegocioException( "Usuário não encontrado" ) );
    }
    
    @Override
    public Usuario salvar( Usuario usuario )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando > {}", usuario );
        
        Usuario usuarioSalvo = repository.save( usuario );
        
        return usuarioSalvo;
    }
    
    public Usuario buscarPorLogin( String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando por Login > {}", login );
        
        Optional<Usuario> usuarioOpt = repository.findByLoginAndAtivoTrue( login );
        
        return usuarioOpt.orElseThrow( ( ) -> new NegocioException( "Usuário não encontrado" ) );
    }
}
