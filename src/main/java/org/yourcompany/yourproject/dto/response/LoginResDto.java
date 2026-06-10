package org.yourcompany.yourproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    private String userId;  // 로그인한 유저 ID
    private String name;    // 유저 이름
    private String role;    // 권한
}