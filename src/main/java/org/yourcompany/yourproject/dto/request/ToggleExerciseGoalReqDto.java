package org.yourcompany.yourproject.dto.request;

import lombok.Data;

@Data
public class ToggleExerciseGoalReqDto {
    private Long assignId;       // 상태를 변경할 운동 과제(exercise)의 고유 식별 번호 (PK)
    private boolean todoGoal;    // 현재 체크박스 상태값 (true/false)
}