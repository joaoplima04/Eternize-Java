package com.example.eternize.eternize.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.eternize.eternize.model.Cliente;
import com.example.eternize.eternize.repository.ClienteRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	ClienteRepository clienteRepository;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Desabilita a segurança para todas as requisições
    	 http
    	 .csrf().disable()
         .authorizeHttpRequests(authorize -> authorize
        		 .requestMatchers("/users/login", "/users/loging_post", "/users/cadastro", "/users/cadastro_post").permitAll()// Permite acesso sem login
                 .anyRequest().authenticated() 
        		 )
         .formLogin(formLogin -> formLogin
        		    .loginPage("/users/login")
        		    .loginProcessingUrl("/users/loging_post") // URL do endpoint de login
        		    .usernameParameter("email")
        		    .defaultSuccessUrl("/", true)
        		    .permitAll()
        		)
    	 .sessionManagement(session -> session
    			 	.sessionCreationPolicy(SessionCreationPolicy.NEVER)
    		        .maximumSessions(1) // Limita para apenas uma sessão ativa por usuário
    		        .expiredUrl("/users/login?expired=true") // URL para redirecionamento quando a sessão expira   
    		    );
         
         
         return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Cliente cliente = clienteRepository.getByEmail(email);
            if (cliente != null) {
            	
                return new User(cliente.getEmail(), cliente.getPassword(), cliente.getAuthorities());
            }
            throw new UsernameNotFoundException("Usuário não encontrado");
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
}