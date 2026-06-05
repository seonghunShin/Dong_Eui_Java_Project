package org.yourcompany.yourproject.util;

import org.springframework.stereotype.Component;
import org.yourcompany.yourproject.dto.request.AddRecordReqDto;
import org.yourcompany.yourproject.entity.Meal;

@Component // 스프링 컨테이너에 빈(Bean)으로 등록하여 Service 계층에서 주입받아 사용할 수 있게 합니다.
public class DietValidator {

    // 의사코드의 오차 범위 ±10% 규격을 비율로 상화 정의
    private static final float MAX_RATE = 1.1f; // 상한선 (+10%)
    private static final float MIN_RATE = 0.9f; // 하한선 (-10%)

    /**
     * 의사코드: 클레스[총칼로리 비교기능] 구현
     * - 회원이 오늘 먹은 누적 칼로리가 트레이너가 설정한 목표 칼로리의 ±10% 범위 내에 있는지 검증합니다.
     */
    public boolean compareCalories(Meal targetMeal, int currentTotalCalories) {
        if (targetMeal == null || targetMeal.getTargetCalories() == 0) {
            return false; // 설정된 목표가 없으면 실패 반환
        }

        int targetCal = targetMeal.getTargetCalories();
        float maxCal = targetCal * MAX_RATE;
        float minCal = targetCal * MIN_RATE;

        // 범위 내에 존재하면 true, 벗어나면 false 반환
        return currentTotalCalories >= minCal && currentTotalCalories <= maxCal;
    }

    /**
     * 의사코드: [탄수화물 / 단백질 / 지방 비교기능] 통합 검증
     * - 3대 영양소가 각각 트레이너가 설정한 목표치의 ±10% 범위 내에 모두 들어왔는지 검사합니다.
     */
    public boolean compareNutrients(Meal targetMeal, int currentCarbs, int currentProtein, int currentFat) {
        if (targetMeal == null) {
            return false;
        }

        return compareCarbs(targetMeal.getTargetCarbs(), currentCarbs) && 
               compareProtein(targetMeal.getTargetProtein(), currentProtein) && 
               compareFat(targetMeal.getTargetFat(), currentFat);
    }

    /**
     * 의사코드: 클레스[탄수화물 비교기능] 상세 알고리즘
     */
    private boolean compareCarbs(int targetCarbs, int currentCarbs) {
        if (targetCarbs == 0) return false;
        return currentCarbs >= (targetCarbs * MIN_RATE) && currentCarbs <= (targetCarbs * MAX_RATE);
    }

    /**
     * 의사코드: 클레스[단백질 비교기능] 상세 알고리즘
     */
    private boolean compareProtein(int targetProtein, int currentProtein) {
        if (targetProtein == 0) return false;
        return currentProtein >= (targetProtein * MIN_RATE) && currentProtein <= (targetProtein * MAX_RATE);
    }

    /**
     * 의사코드: 클레스[지방 비교기능] 상세 알고리즘
     */
    private boolean compareFat(int targetFat, int currentFat) {
        if (targetFat == 0) return false;
        return currentFat >= (targetFat * MIN_RATE) && currentFat <= (targetFat * MAX_RATE);
    }
}