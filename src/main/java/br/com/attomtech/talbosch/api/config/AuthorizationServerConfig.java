package br.com.attomtech.talbosch.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.com.attomtech.talbosch.api.config.token.CustomTokenEnhacer;

@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    public void configure( ClientDetailsServiceConfigurer clients ) throws Exception
    {
        clients.inMemory( )
            .withClient( "talboschapi" )
            .secret( "$2a$10$ABg47QokNnIyL2m7zMWj0OUTwy.EaALzN21Xloj53GnZEC5j3.sou" ) // t@lb0sch@p1
            .scopes( "read", "write" )
            .authorizedGrantTypes( "password", "refresh_token" )
            .accessTokenValiditySeconds( 1800 )
            .refreshTokenValiditySeconds( 3600 * 24 );
    }
    
    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception
    {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain( );
        tokenEnhancerChain.setTokenEnhancers( Arrays.asList( tokenEnhacer( ), accessTokenConverter( ) ) );
        
        endpoints.tokenStore( tokenStore( ) )
            .tokenEnhancer( tokenEnhancerChain )
            .reuseRefreshTokens( false )
            .userDetailsService( userDetailsService )
            .authenticationManager( authenticationManager );
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter( )
    {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter( );
        accessTokenConverter.setSigningKey( "talbosch-api" );
        
        return accessTokenConverter;
    }
    
    @Bean
    public TokenStore tokenStore( )
    {
        return new JwtTokenStore( accessTokenConverter( ) );
        
    }
    
    @Bean
    public TokenEnhancer tokenEnhacer( )
    {
        return new CustomTokenEnhacer( );
    }
}
