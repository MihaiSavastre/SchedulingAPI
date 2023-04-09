package com.softbinator.labs.project.schedulingapi.security.config;

import com.softbinator.labs.project.schedulingapi.security.filters.JwtAuthorizationFilter;
import com.softbinator.labs.project.schedulingapi.security.repositories.UserRepository;
import com.softbinator.labs.project.schedulingapi.security.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //Allow everything
        http.csrf().disable().authorizeHttpRequests().requestMatchers("/**").permitAll();


    //General setup for endpoints, if security is implemented
//        http = http.cors().and().csrf().disable();
//        http = http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and();
//        http = http
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                )
//                .and();
//
//        http.authorizeHttpRequests()
//                //public endpoints
//                .requestMatchers("/api/v1/auth/register/patient/**").permitAll()
//                .requestMatchers("/api/v1/auth/").permitAll()
//                .requestMatchers("/api/v1/api-docs/**").permitAll()
//
//                .requestMatchers("/index.html").permitAll()
//                .requestMatchers("/login").permitAll()
//                //swagger
//                .requestMatchers("/v3/api-docs/**").permitAll()
//                .requestMatchers("/swagger-ui/**").permitAll()
//
//                .requestMatchers("/api/v1/doctors").authenticated()
//                .requestMatchers("/api/v1/patients").authenticated()
//                .requestMatchers("/api/v1/timeslots").authenticated()
//                .requestMatchers("/api/v1/auth/register/doctor").authenticated()
//                .anyRequest().permitAll();
//
//        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}
