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
    private String memberId; // 회원 ID (id:member1) [cite: 68]
    private String name;     // 회원 이름 (김영희, 김철수) [cite: 66, 69]
    private int ptCount;     // 잔여 PT 횟수 (pt 3회, pt 9회) [cite: 67, 70]
}