package org.yourcompany.yourproject.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Exercise {

    private Long assignId;            // 운동 과제 고유 식별 번호 (PK, Auto_Increment)
    private User member;              // 목표를 부여받은 회원 객체 (외래키 매핑)
    private LocalDate targetDate;     // 목표 날짜 (DATE 타입 매핑)
    private String targetExercise;    // 목표 운동 이름
    private int targetBurnedCal;      // 목표 소비 칼로리
    private boolean todoGoal;         // 목표 달성 여부 (T/F)

    /**
     * 의사코드: 목표설정(목표설정Dto, pageId) 내 운동 가이드 수정 지원
     */
    public void updateExerciseTargets(String exerciseName, int burnedCal) {
        this.targetExercise = exerciseName;
        this.targetBurnedCal = burnedCal;
        // 목표가 새로 설정되거나 수정되면 달성 여부를 초기값(false)으로 세팅합니다.
        this.todoGoal = false; 
    }

    /**
     * 의사코드: 클레스[운동완료체크기능] (운동 테이블 todogool -> not 변경) 구현
     * 비즈니스 로직: 회원이 체크박스를 누를 때 현재 상태를 반전(NOT 연산)시킵니다.
     */
    public void toggleGoalStatus() {
        this.todoGoal = !this.todoGoal; // true -> false, false -> true 반전
    }
}