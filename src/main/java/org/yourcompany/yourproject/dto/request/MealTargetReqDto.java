package org.yourcompany.yourproject.dto.request;

import java.time.LocalDate;

import lombok.Data;


// 식사 목표 설정 요청 DTO
public class MealTargetReqDto {

    @Data
    public static class MealTargetReq {
        private LocalDate targetDate;
        private int targetCalories;
        
        private int carbo;
        private int protein;
        private int fat;
    }
}
