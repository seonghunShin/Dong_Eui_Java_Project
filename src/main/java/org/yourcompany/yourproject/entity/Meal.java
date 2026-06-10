package org.yourcompany.yourproject.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY) // Auto_Increment 설정
    private Long assignId;         // 식단 과제 고유 식별 번호 (PK)
    @ManyToOne
    private User member;           // 목표를 부여받은 회원 객체 (외래키 매핑)
    private LocalDate targetDate;  // 목표 날짜 (DATE 타입 매핑)
    private int targetCalories;    // 목표 총 섭취 칼로리
    private int targetCarbs;       // 목표 탄수화물
    private int targetProtein;     // 목표 단백질
    private int targetFat;         // 목표 지방

    // 목표 설정
    public void updateTargets(int calories, int carbs, int protein, int fat) {
        this.targetCalories = calories;
        this.targetCarbs = carbs;
        this.targetProtein = protein;
        this.targetFat = fat;
    }
}