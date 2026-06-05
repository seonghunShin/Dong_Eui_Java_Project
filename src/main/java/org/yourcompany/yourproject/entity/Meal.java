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
public class Meal {

    private Long assignId;         // 식단 과제 고유 식별 번호 (PK, Auto_Increment)
    private User member;           // 목표를 부여받은 회원 객체 (외래키 매핑)
    private LocalDate targetDate;  // 목표 날짜 (DATE 타입 매핑)
    private int targetCalories;    // 목표 총 섭취 칼로리
    private int targetCarbs;       // 목표 탄수화물 (g)
    private int targetProtein;     // 목표 단백질 (g)
    private int targetFat;         // 목표 지방 (g)

    /**
     * 의사코드: 목표설정(목표설정Dto, pageId) 내 식단 가이드 수정 지원
     * 비즈니스 로직: 트레이너가 식단 목표를 새로 입력하거나 수정할 때 사용합니다.
     */
    public void updateTargets(int calories, int carbs, int protein, int fat) {
        this.targetCalories = calories;
        this.targetCarbs = carbs;
        this.targetProtein = protein;
        this.targetFat = fat;
    }
}