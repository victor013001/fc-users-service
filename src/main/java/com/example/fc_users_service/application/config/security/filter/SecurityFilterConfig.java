package com.example.fc_users_service.application.config.security.filter;

import static com.example.fc_users_service.domain.constants.RouterConst.AUTH_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.CLIENT_BASE_PATH;
import static com.example.fc_users_service.domain.constants.RouterConst.USER_BASE_PATH;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterConfig {

  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(AUTH_BASE_PATH + "/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, USER_BASE_PATH + CLIENT_BASE_PATH)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
