package org.yourcompany.yourproject.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 신규회원 등록용 Dto
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRegisterReqDto {
    private String userId;
    private String password;
    private String name;
    private String role;
    private int ptCount;
}