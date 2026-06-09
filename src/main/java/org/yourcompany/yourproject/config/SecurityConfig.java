package org.yourcompany.yourproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            // 👇 여기에 아래 내용을 추가하세요!
            .logout(logout -> logout
                .logoutUrl("/logout")                 // 대시보드에서 보낼 주소
                .logoutSuccessUrl("/login-page")      // 로그아웃 성공 후 이동할 주소
                .invalidateHttpSession(true)          // 세션 무효화
                .deleteCookies("JSESSIONID")          // 쿠키 삭제
            );
            
        return http.build();
    }
}