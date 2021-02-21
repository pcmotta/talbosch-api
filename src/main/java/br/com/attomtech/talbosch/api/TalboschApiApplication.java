package br.com.attomtech.talbosch.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

import br.com.attomtech.talbosch.api.config.property.ApiProperty;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(ApiProperty.class)
public class TalboschApiApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run( TalboschApiApplication.class, args );
    }
}
