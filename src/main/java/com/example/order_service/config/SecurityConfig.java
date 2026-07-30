package com.example.order_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails employee = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("employee123"))
                .roles("EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(admin, employee);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/products/*/delete", "/customers/*/delete", "/orders/*/delete").hasRole("ADMIN")
                        .requestMatchers("/products/**", "/customers/**", "/orders/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.permitAll());

        return http.build();
    }
}
