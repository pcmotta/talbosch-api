package br.com.attomtech.talbosch.api.controller;

import java.util.stream.IntStream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.attomtech.talbosch.api.controller.interfaces.NegocioControllerAuditoria;
import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.model.enums.Permissao;
import br.com.attomtech.talbosch.api.repository.filter.UsuarioFilter;
import br.com.attomtech.talbosch.api.service.UsuarioService;
import br.com.attomtech.talbosch.api.utils.LabelValue;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements NegocioControllerAuditoria<Usuario, UsuarioFilter, Long>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( UsuarioController.class );
    
    @Autowired
    private UsuarioService service;
    
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Page<Usuario>> pesquisar( UsuarioFilter filtro, Pageable pageable )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Usuários" );
        
        Page<Usuario> pagina = service.pesquisar( filtro, pageable );
        
        return ResponseEntity.ok( pagina );
    }
    
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Usuario> cadastrar( @RequestBody @Valid Usuario usuario, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Salvando Usuário >> {}", usuario );
        
        Usuario usuarioSalvo = service.cadastrar( usuario, auth.getName( ) );
        
        return ResponseEntity.status( HttpStatus.CREATED ).body( usuarioSalvo );
    }
    
    @Override
    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Usuario> atualizar( @RequestBody @Valid Usuario usuario, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Atualizando usuário >> {}", usuario );
        
        Usuario usuarioSalvo = service.atualizar( usuario, auth.getName( ) );
        
        return ResponseEntity.ok( usuarioSalvo );
    }
    
    @Override
    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Usuario> buscarPorCodigo( @PathVariable Long codigo )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Usuário >> {}", codigo );
        
        Usuario usuario = service.buscarPorCodigo( codigo );
        
        return ResponseEntity.ok( usuario );
    }
    
    @Override
    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<Void> excluir( @PathVariable Long codigo, Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Deletando usuário >> {}", codigo );
        
        service.excluir( codigo, auth.getName( ) );
        
        return ResponseEntity.ok( ).build( );
    }
    
    @GetMapping("/permissoes")
    @PreAuthorize("hasAuthority('ADMINISTRADOR') or hasAuthority('USUARIO')")
    public ResponseEntity<LabelValue[]> buscarPermissoes( Authentication auth )
    {
        if( LOGGER.isDebugEnabled( ) )
            LOGGER.debug( "Buscando Permissões" );

        Usuario usuario = service.buscarPorLogin( auth.getName( ) );
        Permissao[] permissoes;
        
        if( usuario.isAdministrador( ) )
            permissoes = Permissao.values( );
        else
            permissoes = usuario.getPermissoes( ).stream( ).map( permissao -> permissao.getPermissao( ) )
                    .toArray( size -> new Permissao[size] );
        
        LabelValue[] values = new LabelValue[permissoes.length];
        IntStream.range( 0, permissoes.length ).forEach( index ->
                values[index] = new LabelValue( permissoes[index], permissoes[index].getDescricao( ) ) );
        
        return ResponseEntity.ok( values );
    }
}
