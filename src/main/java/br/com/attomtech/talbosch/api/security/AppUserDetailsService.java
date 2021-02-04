package br.com.attomtech.talbosch.api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.UsuarioRepository;
import br.com.attomtech.talbosch.api.service.UsuarioService;

@Service
public class AppUserDetailsService implements UserDetailsService
{
    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private UsuarioService service;

    @Override
    @Transactional
    public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException
    {
        Optional<Usuario> usuarioOpt = repository.findByLoginAndAtivoTrue( login );
        Usuario usuario = usuarioOpt.orElseThrow( ( ) -> new UsernameNotFoundException( "Usuário e/ou Senha incorretos!" ) );
        
        usuario.setUltimoAcesso( LocalDateTime.ofInstant( Instant.now( ), ZoneId.of( "GMT-3" ) ) );
        service.salvar( usuario );
        
        return new UsuarioSistema( usuario );
    }
}
