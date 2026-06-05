package org.yourcompany.yourproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginReqDto {
    private String userId;     // 사용자가 입력한 ID
    private String password;   // 사용자가 입력한 비밀번호
}