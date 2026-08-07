package com.security.code.config;

import com.security.code.serviceImpl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//Bean that required for Basic Authentication is :-
    //Internally what happens?
    //It configures the basic authentication filter

    @Bean
    public SecurityFilterChain basicAuth(HttpSecurity http){
        http
                .authorizeHttpRequests(auth->auth.requestMatchers("/greet/**")
                                                                               .permitAll()
                                                                               .requestMatchers("/user/**").permitAll()
                                                                               .anyRequest()
                                                                               .authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    //Registered Bean of UserDetailsServcie
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserServiceImpl();
    }

    //Registering Bean of AuthenticationManager and AuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
