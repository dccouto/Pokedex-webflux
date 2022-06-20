package com.couto.pokedex.exceptions;

import org.springframework.boot.autoconfigure.web.WebProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceWebPropertiesConfig {

	// Bean necessário para criar o exceptionHandler
	@Bean
	public WebProperties.Resources resources() {
		return new WebProperties.Resources();
	}

}