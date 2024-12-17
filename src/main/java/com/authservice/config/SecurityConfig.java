package com.authservice.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.authservice.jwt.JwtTokenFilter;
import com.authservice.jwt.JwtTokenProvider;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Use Slf4j para logs (mais padrão)
@Generated
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
				
		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
		return passwordEncoder;
	}
	
    @Bean
    AuthenticationManager authenticationManagerBean(
    		AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
        
        //@formatter:off
        return http
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
            		session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                    authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(
							"/auth/signin",
							"/auth/refresh/**",
                    		"/swagger-ui/**",
                    		"/v3/api-docs/**"
                		).permitAll()
                        .requestMatchers("/auth-service/api/**").authenticated()
                        .requestMatchers("/users").denyAll()
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
        //@formatter:on
    }
    
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();

	    // Definir origens permitidas - substituir pelos domínios reais da sua aplicação
		configuration.setAllowedOriginPatterns(List.of("http://192.168.*.*:*", "http://www.granaflow.local:*", "http://localhost:*"));
	    
	    // Métodos HTTP permitidos
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));

	    // Cabeçalhos permitidos
	    configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

	    // Exposição de cabeçalhos específicos (caso necessário)
	    configuration.setExposedHeaders(List.of("Authorization"));

	    // Configuração para credenciais (se necessário, ex: cookies, autenticação baseada em sessão)
	    configuration.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}
