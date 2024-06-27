package com.mobiauto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/proprietario/**").hasAnyRole("PROPRIETARIO", "ADMIN")
                                .requestMatchers("/assistente/**").hasAnyRole("ASSISTENTE", "PROPRIETARIO", "ADMIN")
                                .anyRequest().authenticated()
                )
                .httpBasic(httpBasicCustomizer ->
                        httpBasicCustomizer.authenticationEntryPoint(authenticationEntryPoint())
                );
        return http.build();
    }

    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("mobiauto");
        return entryPoint;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpassword"))
                .roles("ADMIN")
                .build();

        UserDetails proprietario = User.builder()
                .username("proprietario")
                .password(passwordEncoder().encode("proprietariopassword"))
                .roles("PROPRIETARIO")
                .build();

        UserDetails assistente = User.builder()
                .username("assistente")
                .password(passwordEncoder().encode("assistente"))
                .roles("ASSISTENTE")
                .build();

        return new InMemoryUserDetailsManager(user, admin, proprietario, assistente);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
