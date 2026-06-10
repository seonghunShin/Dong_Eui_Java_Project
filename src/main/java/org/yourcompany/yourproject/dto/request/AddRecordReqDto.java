package org.yourcompany.yourproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecordReqDto {
    private String mealType;  // todayMealType 대응
    private String mealTime;  // todoMealTime 대응
    private String foodName;  
    private int carbo;        // todayCarbs 대응
    private int protein;      // todayprotein 대응
    private int fat;          // todayFat 대응
    private int calories;     // todayCalories 대응
}