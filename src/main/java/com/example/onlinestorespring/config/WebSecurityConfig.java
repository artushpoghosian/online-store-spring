package com.example.onlinestorespring.config;

import com.example.onlinestorespring.security.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public WebSecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/",
                                        "/login",
                                        "/register",
                                        "/logout",
                                        "/css/**",
                                        "/js/**",
                                        "/images/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/categories/**").hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(customLoginSuccessHandler)
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}