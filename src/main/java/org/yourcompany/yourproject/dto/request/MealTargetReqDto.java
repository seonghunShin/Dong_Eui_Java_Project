package org.yourcompany.yourproject.dto;

import java.time.LocalDate;

import lombok.Data;

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
