package br.com.attomtech.talbosch.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.attomtech.talbosch.api.config.property.ApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter
{
    private ApiProperty property;

    @Autowired
    public CorsFilter( ApiProperty property )
    {
        this.property = property;
    }

    @Override
    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException
    {
        HttpServletRequest  request  = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        
        response.setHeader( "Access-Control-Allow-Origin",      property.getOriginPermitida( ) );
        response.setHeader( "Access-Control-Allow-Credentials", Boolean.TRUE.toString( )       );
        
        if( "OPTIONS".equals( request.getMethod( ) ) && property.getOriginPermitida( ).equals( request.getHeader( "Origin" ) ) )
        {
            response.setHeader( "Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS"     );
            response.setHeader( "Access-Control-Allow-Headers", "Authorization, Content-Type, Accept" );
            response.setHeader( "Access-Control-Max-Age",       "3600"                                );
            
            response.setStatus( HttpServletResponse.SC_OK );
        }
        else
            chain.doFilter( req, resp );
    }
    
    
}
