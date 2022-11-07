package com.watchtogether.server.config;

import com.watchtogether.server.components.jwt.JwtAuthenticationFilter;
import com.watchtogether.server.exception.handler.AuthenticationEntryPointHandler;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // cors 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(
            Arrays.asList("http://localhost:3000"));    // 허용할 도메인 정보
        configuration.setAllowedMethods(Arrays.asList("*"));    // 허용할 http 메소드
        configuration.setAllowedHeaders(Arrays.asList("*"));    // 허용할 헤더 정보
        configuration.setAllowCredentials(true);    // cross origin 으로부터 인증을 위한 쿠키 정보를 받을지 여부
        configuration.setMaxAge(3600L); //preflight 결과를 1시간동안 캐시에 저장

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // Spring Security 적용하지 않을 리소스 설정
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
            .antMatchers(
                "/v3/api-docs/**",
                "/api-docs/**",
                "/api-document/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                    "/**"
            );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .httpBasic().disable()
            .csrf().disable()   //csrf 보안 토큰 diable 처리
            .formLogin().disable()
            .cors() //cors설정

            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new AuthenticationEntryPointHandler())

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션은 사용하지 않음

            .and()
            .authorizeRequests()    //요청에 대한 권한 체크
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //  Preflight 요청은 허용
            .antMatchers(
                "/api/v1/refresh-token",
                "/api/v1/users/sign-in",
                "/api/v1/users/sign-up/**",
                "/api/v1/users/reset-password/**",
                    "/**").permitAll()
            .anyRequest().authenticated()

            .and()
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

            .build();

    }
}
