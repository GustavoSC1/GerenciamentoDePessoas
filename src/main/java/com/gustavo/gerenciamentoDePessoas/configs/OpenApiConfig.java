package com.gustavo.gerenciamentoDePessoas.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(
						new Info()
							.title("Gerenciamento de Pessoas")
							.description("API simples para gerenciar Pessoas")
							.version("v1")
							.license(
								licence()
							)
							.contact(
									contact()
							));
	}
	
	private License licence() {
		return new License().name("Apache 2.0").url("Apache 2.0");
	}
	
	private Contact contact() {
		return new Contact().name("Gustavo da Silva Cruz").email("Gustavo da Silva Cruz").url("Gustavo da Silva Cruz");
	}

}
