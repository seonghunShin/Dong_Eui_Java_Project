package org.yourcompany.yourproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerMemberListResDto {
    private String memberId; // 회원 ID
    private String name;     // 회원 이름
    private int ptCount;     // 잔여 PT 횟수
}