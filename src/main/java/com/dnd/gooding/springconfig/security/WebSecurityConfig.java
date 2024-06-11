package com.dnd.gooding.springconfig.security;

import com.dnd.gooding.springconfig.log.MDCLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private MDCLoggingFilter mdcLoggingFilter;

    public WebSecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter, MDCLoggingFilter mdcLoggingFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.mdcLoggingFilter = mdcLoggingFilter;
    }

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        return http.cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .antMatchers("/actuator/**")
                                        .permitAll()
                                        .antMatchers("/oauth/**")
                                        .permitAll()
                                        .antMatchers("/api/v1/oauth/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(mdcLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .antMatcher("/api/**")
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
