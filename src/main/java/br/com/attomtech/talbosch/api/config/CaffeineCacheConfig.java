package br.com.attomtech.talbosch.api.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CaffeineCacheConfig
{
    @Bean
    public CacheManager cacheManager( )
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager( );
        cacheManager.setCaffeine( caffeineCacheBuilder( ) );
        
        return cacheManager;
    }
    
    private Caffeine<Object, Object> caffeineCacheBuilder( )
    {
        return Caffeine.newBuilder( )
                .initialCapacity( 100 )
                .maximumSize( 500 )
                .expireAfterAccess( 15, TimeUnit.MINUTES )
                .weakKeys( )
                .recordStats( );
    }
}
