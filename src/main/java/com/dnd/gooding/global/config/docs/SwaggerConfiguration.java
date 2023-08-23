package com.dnd.gooding.global.config.docs;

import static org.springframework.http.HttpHeaders.*;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {

	private static final String BEARER_TYPE = "Bearer";
	private static final String JWT = "Jwt";

	@Bean
	public OpenAPI openApi(
		@Value("${springdoc.title}") String title,
		@Value("${springdoc.version}") String springdocVersion,
		@Value("${springdoc.description}") String description) {
		Components components = new Components()
			.addSecuritySchemes(AUTHORIZATION, new SecurityScheme()
				.name(AUTHORIZATION)
				.type(SecurityScheme.Type.HTTP)
				.scheme(BEARER_TYPE)
				.bearerFormat(JWT));

		SecurityRequirement securityRequirement = new SecurityRequirement().addList(AUTHORIZATION);

		Info info = new Info()
			.title(title)
			.version(springdocVersion)
			.description(description);

		return new OpenAPI()
			.addServersItem(new Server().url("/"))
			.components(components)
			.addSecurityItem(securityRequirement)
			.info(info);
	}

	@Bean
	public GroupedOpenApi userGroup() {
		return GroupedOpenApi.builder()
			.group("User")
			.pathsToMatch("/api/v1/user/**")
			.build();
	}

	@Bean
	public GroupedOpenApi tokenGroup() {
		return GroupedOpenApi.builder()
			.group("Token")
			.pathsToMatch("/api/v1/tokens/**")
			.build();
	}

	@Bean
	public GroupedOpenApi oauthGroup() {
		return GroupedOpenApi.builder()
			.group("OAuth")
			.pathsToMatch("/oauth/**")
			.build();
	}

	@Bean
	public GroupedOpenApi recordGroup() {
		return GroupedOpenApi.builder()
			.group("Record")
			.pathsToMatch("/api/v1/record/**")
			.build();
	}

	@Bean
	public GroupedOpenApi feedGroup() {
		return GroupedOpenApi.builder()
			.group("Feed")
			.pathsToMatch("/api/v1/feed/**")
			.build();
	}

	@Bean
	public GroupedOpenApi onboardGroup() {
		return GroupedOpenApi.builder()
				.group("Onboard")
				.pathsToMatch("/api/v1/onboard/**")
				.build();
	}
}
