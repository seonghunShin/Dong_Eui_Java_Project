package org.yourcompany.yourproject.dto.response;

import java.util.List;

import org.yourcompany.yourproject.entity.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTodayDashboardResDto {

    private String name;
    // 오늘의 운동 할당량 정보
    private Long assignId;               // 운동 과제 고유 코드
    private String targetExerciseName;   // 목표 운동 이름
    private int targetBurnedCalories;    // 목표 소비 칼로리
    private boolean isExerciseGoalDone;  // 운동 완료 체크 여부

    // 권장 식단 목표치
    private int targetCalories;          // 목표 총 섭취 칼로리
    private int targetCarbs;             // 목표 탄수화물
    private int targetProtein;           // 목표 단백질
    private int targetFat;               // 목표 지방

    // 금일 실제 섭취 총합
    private int currentTotalCalories;    // 오늘 먹은 총 칼로리 누적값
    private int currentTotalCarbs;       // 오늘 먹은 총 탄수화물 누적값
    private int currentTotalProtein;     // 오늘 먹은 총 단백질 누적값
    private int currentTotalFat;         // 오늘 먹은 총 지방 누적값

    // 하단에 출력할 오늘 기록한 식단 리스트
    private List<Record> todayMealRecords; 
}