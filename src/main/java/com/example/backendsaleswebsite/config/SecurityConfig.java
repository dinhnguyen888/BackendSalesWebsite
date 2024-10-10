package com.example.backendsaleswebsite.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final String[] END_POINT = {"/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**",
										"/login", "/register", "/introspect"};
	
	private String SIGNER_KEY = "GaHQG6SjkCOQBF5yspA4Bd+t1EGA1gP+UP++0odDou9MUNdArwKwCX1kmqtSlEhQ";
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
			.authorizeHttpRequests(request -> 
			request.requestMatchers(END_POINT).permitAll()
			.anyRequest().authenticated());
    	
    	httpSecurity
    		.oauth2ResourceServer(oauth2 ->
    		oauth2.jwt(JwtConfigurer -> JwtConfigurer.decoder(jwtDecoder())));
    	
    	httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
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
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}