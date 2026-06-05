package org.yourcompany.yourproject.dto;

import java.time.LocalDate;

import lombok.Data;

public class ExerciseTargetReqDto {

    @Data
    public static class ExerciseTargetReq {
        private LocalDate targetDate;
        private String exerciseName;
        private int targetCalories;
    }
    
}
