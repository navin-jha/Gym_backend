package com.app.demo.config;

import com.app.demo.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // ✅ Enable CORS
                .cors(cors -> {
                })

                // ❌ Disable CSRF for API
                .csrf(csrf -> csrf.disable())

                // ✅ Stateless session for JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/member/register").permitAll()
                        .requestMatchers("/plans/**").permitAll()
                        .requestMatchers("/member/**").permitAll()
                        .requestMatchers("/trainer/**").permitAll()
                        .requestMatchers("/payment/**").permitAll()
                        .requestMatchers("/attendance/**").permitAll()

                        // Role based access
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/trainer/**").hasRole("TRAINER")

                        // Everything else
                        .anyRequest().authenticated())

                // ✅ Add JWT filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}