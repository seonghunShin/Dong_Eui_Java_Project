package org.yourcompany.yourproject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.yourcompany.yourproject.dto.request.LoginReqDto;
import org.yourcompany.yourproject.dto.request.NewUserRegisterReqDto;
import org.yourcompany.yourproject.dto.response.LoginResDto;
import org.yourcompany.yourproject.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 공통 로그인 화면 이동
    @GetMapping("/login-page")
    public String loginPage() {
        return "loginForm"; // templates/login.html
    }

    // 역할군에 맞는 로그인
    @PostMapping("/user/login")
    public String login(@ModelAttribute LoginReqDto loginReqDto, HttpSession session) {
        try {
            // UserService를 통해 로그인 검증 및 권한 획득
            LoginResDto loginRes = userService.login(loginReqDto);

            // 로그인 성공 시 세션에 유저 정보 저장
            session.setAttribute("loginUser", loginRes);

            // 멤버 트레이너 구분
            if ("TRAINER".equals(loginRes.getRole())) {
                return "redirect:/trainer/members"; // 트레이너 메인 라우팅
            } else {
                return "redirect:/member/dashboard"; // 멤버 메인 라우팅
            }
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 에러 메시지를 들고 로그인 페이지로 리다이렉트
            return "redirect:/login-page?error=" + e.getMessage();
        }
    }

    // 회원가입 처리
    @PostMapping("/user/join")
    public String join(@ModelAttribute NewUserRegisterReqDto registerDto) {
        userService.registerMember(registerDto);
        return "redirect:/login-page"; 
    }

    // 로그아웃
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃)
        return "redirect:/login-page"; // 로그인 라우팅으로 이동
    }
}