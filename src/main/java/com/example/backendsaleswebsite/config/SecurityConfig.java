package com.example.backendsaleswebsite.config;

import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final String[] END_POINT = {"/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**",
										"/auth/**", "/categories/**", "/vnpay-payment", "/submitOrder" , "/api/orders", "/api/products/pages", "/api/products/searchByName", "/api/products/searchByManufacturer", "/api/products/**", "/api/reviews/**"};


	private String SIGNER_KEY = "GaHQG6SjkCOQBF5yspA4Bd+t1EGA1gP+UP++0odDou9MUNdArwKwCX1kmqtSlEhQ";
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
        .authorizeHttpRequests(request -> 
            request.requestMatchers(END_POINT).permitAll()
            .anyRequest().authenticated());
    
    // Configure CORS with the new approach
    CorsConfigurer<HttpSecurity> corsConfigurer = httpSecurity.getConfigurer(CorsConfigurer.class);
    if (corsConfigurer != null) {
        corsConfigurer.configurationSource(corsConfigurationSource());
    }

    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(oauth2 -> 
            oauth2.jwt(JwtConfigurer -> JwtConfigurer.decoder(jwtDecoder())));

    return httpSecurity.build();
    }
    
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");  // Allow all origins
        config.addAllowedHeader("*");         // Allow all headers
        config.addAllowedMethod("*");         // Allow all HTTP methods

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
    
    @Bean
    JwtDecoder jwtDecoder() {
    	SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HS512");
    			
    	return NimbusJwtDecoder
    					.withSecretKey(secretKeySpec)
    					.macAlgorithm(MacAlgorithm.HS512)
    					.build();
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API").version("1.0"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
    }
    
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}