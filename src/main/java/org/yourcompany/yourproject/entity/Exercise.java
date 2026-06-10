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
public class Exercise {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long assignId;            // 운동 과제 고유 식별 번호 (PK, Auto_Increment)
    @ManyToOne
    private User member;              // 목표를 부여받은 회원 객체 (외래키 매핑)
    private LocalDate targetDate;     // 목표 날짜 (DATE 타입 매핑)
    private String targetExercise;    // 목표 운동 이름
    private int targetBurnedCal;      // 목표 소비 칼로리
    private boolean todoGoal;         // 목표 달성 여부

    // 목표 설정
    public void updateExerciseTargets(String exerciseName, int burnedCal) {
        this.targetExercise = exerciseName;
        this.targetBurnedCal = burnedCal;
        this.todoGoal = false; 
    }

    // 운동 완료 체크
    public void toggleGoalStatus() {
        this.todoGoal = !this.todoGoal; // true -> false, false -> true 반전
    }
}