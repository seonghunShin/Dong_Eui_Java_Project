package org.yourcompany.yourproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data; // 또는 lombok.Getter;
import lombok.NoArgsConstructor;

@Data // ★ 중요: 이게 있어야 getMealType(), getMealTime() 등의 Getter 메서드가 자동으로 생성됩니다!
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRecordReqDto {
    private String mealType;  // 의사코드의 todayMealType 대응
    private String mealTime;  // 의사코드의 todoMealTime 대응
    private String foodName;  
    private int carbo;        // 의사코드의 todayCarbs 대응
    private int protein;      // 의사코드의 todayprotein 대응
    private int fat;          // 의사코드의 todayFat 대응
    private int calories;     // 의사코드의 todayCalories 대응
}