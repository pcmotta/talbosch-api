package br.com.attomtech.talbosch.api.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.attomtech.talbosch.api.model.Usuario;
import br.com.attomtech.talbosch.api.repository.UsuarioRepository;
import br.com.attomtech.talbosch.api.service.MensagemService;

@Service
public class AppUserDetailsService implements UserDetailsService
{
    private UsuarioRepository repository;
    private MensagemService mensagemService;
    
    @Autowired
    public AppUserDetailsService( UsuarioRepository repository, MensagemService service )
    {
        this.repository = repository;
        this.mensagemService = service;
    }
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException
    {
        Optional<Usuario> usuarioOpt = repository.findByLoginAndAtivoTrue( login );
        Usuario usuario = usuarioOpt.orElseThrow( ( ) -> new UsernameNotFoundException( "Usuário e/ou Senha incorretos!" ) );
        int mensagensNaoLidas = mensagemService.buscarNaoLidas( login );
        
        return new UsuarioSistema( usuario, mensagensNaoLidas );
    }
}
