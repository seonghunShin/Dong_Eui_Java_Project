package org.yourcompany.yourproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToggleExerciseGoalReqDto {
    private Long assignId;       // 상태를 변경할 운동 과제의 고유 식별 번호 (PK)
    private boolean todoGoal;    // 현재 체크박스 상태값
}