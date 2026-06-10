package org.yourcompany.yourproject.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TotalTargetAssignReqDto {
    private String memberId;             // 목표를 부여받을 회원 ID
    private LocalDate targetDate;        // 트레이너가 달력에서 선택한 날짜

    // 식단 권장 가이드
    private int targetCalories;          // 목표 총 섭취 칼로리
    private int targetCarbs;             // 목표 탄수화물
    private int targetProtein;           // 목표 단백질
    private int targetFat;               // 목표 지방

    // 운동 권장 가이드
    private String targetExerciseName;   // 목표 운동 이름
    private int targetBurnedCalories;    // 목표 소비 칼로리
}