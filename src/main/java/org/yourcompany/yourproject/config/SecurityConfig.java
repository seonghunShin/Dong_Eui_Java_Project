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
            .csrf(csrf -> csrf.disable()) // 💡 성훈님의 컨트롤러가 POST 요청을 받을 수 있도록 CSRF 비활성화
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 💡 시큐리티의 강제 차단 해제 (접근 제어는 성훈님의 컨트롤러가 직접 함!)
            );
            
            // 🎯 formLogin(...) 블록을 완전히 삭제했습니다.
            // 이제 /user/login 요청이 들어오면 시큐리티가 가로채지 않고, 
            // 성훈님의 UserController로 100% 안전하게 전달됩니다.

        return http.build();
    }
}