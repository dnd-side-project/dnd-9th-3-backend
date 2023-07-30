package com.dnd.gooding.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dnd.gooding.global.oauth.handler.CustomOAuth2FailureHandler;
import com.dnd.gooding.global.oauth.handler.CustomOAuth2SuccessHandler;
import com.dnd.gooding.global.oauth.repository.HttpCookieOAuthAuthorizationRequestRepository;
import com.dnd.gooding.global.token.filter.JwtAuthenticationFilter;
import com.dnd.gooding.global.token.handler.ExceptionHandlerFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ExceptionHandlerFilter exceptionHandlerFilter;
	private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
	private final CustomOAuth2FailureHandler customOAuth2FailureHandler;
	private final HttpCookieOAuthAuthorizationRequestRepository httpCookieOAuthAuthorizationRequestRepository;

	@Bean
	public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
		return http
			.cors()
			.configurationSource(corsConfigurationSource())
			.and()
			.authorizeHttpRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/health-check").permitAll()
			.antMatchers("/static/js/**").permitAll()
			.antMatchers("/static/images/**").permitAll()
			.antMatchers("/static/css/**").permitAll()
			.antMatchers("/static/scss/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/v3/api-docs/**").permitAll()
			.antMatchers("/api/v1/doc/**").permitAll()
			.antMatchers("/api/v1/tokens/temp").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic().disable()
			.rememberMe().disable()
			.csrf().disable()
			.logout().disable()
			.requestCache().disable()
			.formLogin().disable()
			.headers().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorization")
			.authorizationRequestRepository(httpCookieOAuthAuthorizationRequestRepository)
			.and()
			.successHandler(customOAuth2SuccessHandler)
			.failureHandler(customOAuth2FailureHandler)
			.and()
			.exceptionHandling()
			.and()
			.addFilterBefore(jwtAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter.class)
			.addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
			.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
