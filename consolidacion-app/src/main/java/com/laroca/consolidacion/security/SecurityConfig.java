package com.laroca.consolidacion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF desactivado: es un panel interno de una sola iglesia, sin
            // formularios de terceros incrustados. Si mas adelante esto crece
            // a un servicio publico con mas riesgo, conviene revisar esto.
            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                // ---- Lado publico: la web de la iglesia y el formulario de registro ----
                .requestMatchers(
                    "/", "/index.html", "/styles.css",
                    "/registro.html", "/consolidacion.css", "/logo-la-roca.png",
                    "/login.html", "/perform_login"
                ).permitAll()
                // Cualquiera puede ENVIAR el formulario de registro
                .requestMatchers(HttpMethod.POST, "/api/asistentes").permitAll()

                // ---- Lado administrador: solo con sesion iniciada ----
                .requestMatchers("/consolidacion.html").authenticated()
                .requestMatchers("/api/asistentes/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/asistentes").authenticated()

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/consolidacion.html", true)
                .failureUrl("/login.html?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?salida=true")
                .permitAll()
            );

        return http.build();
    }
}
