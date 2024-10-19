package com.example.Springbootproject.Security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity 
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
		@Autowired
	 	private JWTAuthConverter jwtAuthConverter;
		
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	        return httpSecurity
	        		.cors(Customizer.withDefaults())
	        		.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        		.csrf(csrf->csrf.disable())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/users").hasAuthority("ADMIN"))
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/confirm-account").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/users/email/{email}").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/users/email/{email}/{password}").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/users/account-validation/{email}").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/users/otp-validation").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("verifications").permitAll())
	        		.authorizeHttpRequests(ar->ar.requestMatchers("/auth").permitAll())
	                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
	                .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
	                .build();
	    }
	    
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        configuration.setExposedHeaders(Arrays.asList("*"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
      