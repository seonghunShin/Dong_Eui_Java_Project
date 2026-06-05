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
    // 1. 오늘의 운동 할당량 정보 (의사코드의 todayAssige 및 기획서 [그림4] 반영)
    private Long assignId;               // 운동 과제 고유 코드
    private String targetExerciseName;   // 목표 운동 이름 (예: "스쿼트 100개, 푸시업 50개") [cite: 142]
    private int targetBurnedCalories;    // 목표 소비 칼로리 (예: 400 kcal) [cite: 141]
    private boolean isExerciseGoalDone;  // 운동 완료 체크 여부 (todoGoal -> true/false)

    // 2. 권장 식단 목표치 (CaloriesComparison, NutrientComparison 검증의 기준 값)
    private int targetCalories;          // 목표 총 섭취 칼로리 (예: 2200 kcal) [cite: 148]
    private int targetCarbs;             // 목표 탄수화물 (g) (예: 250g) [cite: 152]
    private int targetProtein;           // 목표 단백질 (g) (예: 250g) [cite: 153]
    private int targetFat;               // 목표 지방 (g) (예: 250g) [cite: 164]

    // 3. 금일 실제 섭취 총합 (의사코드의 [총칼로리/탄/단/지 비교기능] 연산 결과물)
    private int currentTotalCalories;    // 오늘 먹은 총 칼로리 누적값
    private int currentTotalCarbs;       // 오늘 먹은 총 탄수화물 누적값
    private int currentTotalProtein;     // 오늘 먹은 총 단백질 누적값
    private int currentTotalFat;         // 오늘 먹은 총 지방 누적값

    // 4. 하단에 출력할 오늘 기록한 식단 리스트 (의사코드의 todayRecord[] 배열을 대체하는 우아한 List)
    private List<Record> todayMealRecords; 
}