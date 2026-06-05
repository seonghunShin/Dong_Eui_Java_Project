package org.yourcompany.yourproject.dto;
import org.yourcompany.yourproject.entity.User;

import lombok.Data;

// 신규회원 등록용 Dto
@Data
public class NewUserRegisterReqDto {
    private String userId;
    private String password;
    private String name;
    private String role;
    private int ptCount;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .name(name)
                .role(role)
                .ptCount(ptCount)
                .build();
    }
}