package org.yourcompany.yourproject.service;

import org.springframework.security.core.userdetails.UserDetails;
// 💡 파일 맨 위쪽 import문 모여있는 곳에 이 두 줄을 꼭 넣어주세요!
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yourcompany.yourproject.dto.request.LoginReqDto;
import org.yourcompany.yourproject.dto.request.NewUserRegisterReqDto;
import org.yourcompany.yourproject.dto.response.LoginResDto;
import org.yourcompany.yourproject.entity.User;
import org.yourcompany.yourproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 의사코드: 클래스 로그인(id, password) 검증 로직 구현
     */
    @Transactional(readOnly = true)
    public LoginResDto login(LoginReqDto dto) {
        // 1. 유저 ID 조회 (없으면 예외 발생)
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        // 2. 비밀번호 일치 확인 (의사코드의 id, password 있는지 확인 부문)
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 컨트롤러 라우팅에 필요한 핵심 정보 반환
        return LoginResDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    /**
     * 신규 회원가입 처리 로직
     */
    @Transactional
    public void registerMember(NewUserRegisterReqDto dto) {
        if (userRepository.existsById(dto.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .name(dto.getName())
                .role(dto.getRole())
                .ptCount(dto.getPtCount())
                .build();

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 시큐리티가 넘겨준 아이디(username)로 DB 조회
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username));

        // 시큐리티가 비밀번호 비교를 직접 할 수 있도록 규격 팩에 담아서 던져줌
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword()) // DB에 저장된 암호화된 비밀번호 ($2a$10$...)
                .roles(user.getRole())        // MEMBER 혹은 TRAINER 역할
                .build();
    }
}