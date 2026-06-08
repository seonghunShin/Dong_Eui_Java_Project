package org.yourcompany.yourproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 비밀번호 암호화 도구 (회원가입 시 비밀번호를 암호화해서 저장할 때 사용)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. 보안 설정 (어떤 페이지를 열어주고 막을지 결정)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 테스트를 위해 CSRF(보안 공격 방지) 잠시 해제
            .authorizeHttpRequests(auth -> auth
                // 이 페이지들은 로그인 안 해도 들어갈 수 있음
                .requestMatchers("/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                // 나머지는 무조건 로그인 해야 함
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")        // 로그인 페이지 주소
                .loginProcessingUrl("/user/login") // 로그인 폼이 데이터를 보낼 주소
                .defaultSuccessUrl("/user/dashboard", true) // 로그인 성공 시 이동할 곳
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            );

        return http.build();
    }
}