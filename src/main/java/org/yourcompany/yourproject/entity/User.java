package org.yourcompany.yourproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class User {

    private String userId;     // 사용자 아이디 (PK)
    private String password;   // 비밀번호
    private String name;       // 이름
    private String role;       // 권한 ("TRAINER" 또는 "MEMBER")
    private String trainerId;  // (회원 전용) 담당 트레이너 ID (외래키 역할)
    private int ptCount;       // (회원 전용) 잔여 PT 횟수

    /**
     * 의사코드: public void PT 횟수 변경(userId, newPTCount) 지원
     * 비즈니스 로직: 잔여 PT 횟수를 새로운 값으로 변경합니다.
     */
    public void updatePtCount(int newPtCount) {
        if (newPtCount < 0) {
            throw new IllegalArgumentException("PT 횟수는 0보다 작을 수 없습니다.");
        }
        this.ptCount = newPtCount;
    }

    /**
     * 의사코드: public void 회원삭제(pageUserId, trainerId) 지원
     * 비즈니스 로직: DB에서 탈퇴시키는 대신 담당 트레이너 연결만 끊어 데이터 무결성을 지킵니다.
     */
    public void disconnectTrainer() {
        this.trainerId = null;
    }
}