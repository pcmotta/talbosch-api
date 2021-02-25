package br.com.attomtech.talbosch.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.attomtech.talbosch.api.dto.UsuarioDTO;
import br.com.attomtech.talbosch.api.exception.NegocioException;
import br.com.attomtech.talbosch.api.log.UsuarioLog;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.AcaoLog;
import br.com.attomtech.talbosch.api.model.enums.Permissao;
import br.com.attomtech.talbosch.api.repository.UsuarioRepository;
import br.com.attomtech.talbosch.api.repository.filter.UsuarioFilter;
import br.com.attomtech.talbosch.api.service.interfaces.NegocioServiceAuditoria;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@Service
@CacheConfig(cacheNames = "usuarios")
public class UsuarioService extends AuditoriaService<Usuario> implements NegocioServiceAuditoria<Usuario, UsuarioFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( UsuarioService.class );
    
    private UsuarioRepository repository;
    private UsuarioLog usuarioLog;
    private PasswordEncoder passwordEncoder;
    
    public UsuarioService( UsuarioRepository repository, UsuarioLog log, PasswordEncoder encoder )
    {
        this.repository = repository;
        this.usuarioLog = log;
        this.passwordEncoder = encoder;
    }
    
    @Override
    public Page<Usuario> pesquisar( UsuarioFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Pesquisando usuários" );
        
        Page<Usuario> pagina = repository.pesquisar( filtro, pageable );
        
        return pagina;
    }
    
    @Cacheable(key = "'todos'")
    public List<UsuarioDTO> buscarUsuarios( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando usuários" );

        List<Usuario> usuarios = repository.findAll( );
        List<UsuarioDTO> usuariosDto = new ArrayList<UsuarioDTO>( );
        
        usuarios.stream( ).filter( u -> u.getCodigo( ) > 1 ).forEach( usuario -> 
            usuariosDto.add( new UsuarioDTO( usuario ) ) );
        
        return usuariosDto;
    }
    
    @CacheEvict(key = "'todos'")
    @Override
    public Usuario cadastrar( Usuario usuario, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Cadastrando > {}", usuario );
        
        atualizarAuditoriaInclusao( usuario, login );
        usuario.setSenha( passwordEncoder.encode( usuario.getSenha( ) ) );
        
        Usuario usuarioSalvo = salvar( usuario );
        usuarioLog.logar( AcaoLog.CADASTRO, usuarioSalvo.getCodigo( ), usuarioSalvo );
        
        return usuarioSalvo;
    }
    
    @Caching(evict = { @CacheEvict(key = "#usuario.codigo"), @CacheEvict(key = "'todos'") })
    @Override
    public Usuario atualizar( Usuario usuario, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando > {}", usuario );
        
        if( usuario.isNovo( ) )
            throw new NegocioException( "Usuário não fornecido" );
        
        Usuario usuarioSalvo = buscarPorCodigo( usuario.getCodigo( ) ).clone( );
        
        if( StringUtils.hasText( usuario.getSenha( ) ) )
            usuario.setSenha( passwordEncoder.encode( usuario.getSenha( ) ) );
        else
            usuario.setSenha( usuarioSalvo.getSenha( ) );
        
        atualizarAuditoriaAlteracao( usuarioSalvo, login );
        usuario.setAuditoria( usuarioSalvo.getAuditoria( ) );
        usuario.setUltimoAcesso( usuarioSalvo.getUltimoAcesso( ) );
        
        usuario = salvar( usuario ); 
        usuarioLog.logar( AcaoLog.ALTERACAO, usuarioSalvo.getCodigo( ), usuarioSalvo, usuario );
        
        return usuario;
    }
    
    @CacheEvict(key = "'todos'")
    public Usuario atualizarProprioUsuario( Usuario usuario, String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando Próprio Usuário > {}", usuario );
        
        Usuario usuarioSalvo = buscarPorLogin( login ).clone( );
        
        usuario.setCodigo( usuarioSalvo.getCodigo( ) );
        usuario.setPermissoes( usuarioSalvo.getPermissoes( ) );
        usuario.setAtivo( usuarioSalvo.isAtivo( ) );
        
        if( StringUtils.hasText( usuario.getSenha( ) ) )
            usuario.setSenha( passwordEncoder.encode( usuario.getSenha( ) ) );
        else
            usuario.setSenha( usuarioSalvo.getSenha( ) );
        
        atualizarAuditoriaAlteracao( usuarioSalvo, login );
        usuario.setAuditoria( usuarioSalvo.getAuditoria( ) );
        
        usuario = salvar( usuario );
        usuarioLog.logar( AcaoLog.ALTERACAO, usuario.getCodigo( ), usuarioSalvo, usuario );
        
        return usuario;
    }
    
    @Caching(evict = { @CacheEvict(key = "#codigo"), @CacheEvict(key = "'todos'") })
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
        usuarioLog.logar( AcaoLog.EXCLUSAO, codigo, usuario );
    }
    
    @Cacheable(key = "#codigo")
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
    
    @Cacheable(key = "#login")
    public LabelValue[] buscarPermissoes( String login )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Permissões" );

        Usuario usuario = buscarPorLogin( login );
        Permissao[] permissoes;
        
        if( usuario.isAdministrador( ) )
            permissoes = Permissao.values( );
        else
            permissoes = usuario.getPermissoes( ).stream( ).toArray( size -> new Permissao[size] );
        
        LabelValue[] values = new LabelValue[permissoes.length];
        IntStream.range( 0, permissoes.length ).forEach( index ->
                values[index] = new LabelValue( permissoes[index], permissoes[index].getDescricao( ) ) );
        
        return values;
    }
    
    public List<Usuario> buscarUsuariosParaNotificarEstoque( )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Usuários para notificar estoque" );
        
        return repository.usuariosParaNotificarEstoque( );
    }
}
