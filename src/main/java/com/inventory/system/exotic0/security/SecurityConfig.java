package com.inventory.system.exotic0.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        List<UserDetails> users = new ArrayList<>();

        PasswordEncoder passwordEncoder = passwordEncoder();
        UserDetails user1 = User
                .withUsername("admin")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN")
                .build();
        users.add(user1);

        UserDetails user2 = User
                .withUsername("User")
                .password(passwordEncoder.encode("5678"))
                .roles("SELLER")
                .build();
        users.add(user2);

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
            .csrf(csrf -> csrf.disable())
            .formLogin(formLogin ->
                    formLogin
                            .loginPage("/login")
                            .permitAll()
                            .failureUrl("/login-error")
            )
            .logout(logout -> logout.logoutSuccessUrl("/login"))
            .securityMatchers((matchers) -> matchers
                    .requestMatchers("/api/**", "/css/**", "/js/**", "/images/**")
            )
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers( "/api/**").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/js/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .anyRequest().authenticated()
            )
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .userDetailsService(customUserDetailsService)
//                .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
