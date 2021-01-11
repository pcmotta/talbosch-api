package br.com.attomtech.talbosch.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.attomtech.talbosch.api.config.property.ApiProperty;

@EnableConfigurationProperties(ApiProperty.class)
@SpringBootApplication
public class TalboschApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalboschApiApplication.class, args);
	}

}
