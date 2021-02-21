package br.com.attomtech.talbosch.api.config.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.attomtech.talbosch.api.model.enums.Permissao;
import br.com.attomtech.talbosch.api.security.UsuarioSistema;

public class CustomTokenEnhacer implements TokenEnhancer
{
    @Override
    public OAuth2AccessToken enhance( OAuth2AccessToken accessToken, OAuth2Authentication authentication )
    {
        UsuarioSistema  usuarioSistema = (UsuarioSistema)authentication.getPrincipal( );
        List<Permissao> permissoes = usuarioSistema.getUsuario( ).getPermissoes( );
        
        Map<String, Object> addInfo = new HashMap<>( );
        addInfo.put( "codigo",     usuarioSistema.getUsuario( ).getCodigo( ) );
        addInfo.put( "nome",       usuarioSistema.getUsuario( ).getNome( )   );
        addInfo.put( "login",      usuarioSistema.getUsuario( ).getLogin( )  );
        addInfo.put( "permissoes", permissoes                                );
        addInfo.put( "mensagens",  usuarioSistema.getMensagens( )            );

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation( addInfo );
        
        return accessToken;
    }
}
