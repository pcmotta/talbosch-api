package br.com.attomtech.talbosch.api.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.attomtech.talbosch.api.model.Usuario;

public class UsuarioSistema extends User
{
    private static final long serialVersionUID = 1L;

    private Usuario usuario;

    public UsuarioSistema( Usuario usuario )
    {
        super( usuario.getLogin( ), usuario.getSenha( ), 
                usuario.getPermissoes( ).stream( ).map( permissao -> 
                    new SimpleGrantedAuthority( permissao.toString( ) ) ).collect( Collectors.toList( ) ) );
        this.usuario = usuario;
    }
    
    public Usuario getUsuario( )
    {
        return this.usuario;
    }
}
