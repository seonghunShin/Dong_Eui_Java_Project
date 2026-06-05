package org.yourcompany.yourproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yourcompany.yourproject.dto.request.AddRecordReqDto;
import org.yourcompany.yourproject.dto.request.ToggleExerciseGoalReqDto;
import org.yourcompany.yourproject.dto.response.MemberTodayDashboardResDto;
import org.yourcompany.yourproject.entity.Exercise;
import org.yourcompany.yourproject.entity.Meal;
import org.yourcompany.yourproject.entity.Record;
import org.yourcompany.yourproject.entity.User;
import org.yourcompany.yourproject.repository.ExerciseRepository;
import org.yourcompany.yourproject.repository.MealRepository;
import org.yourcompany.yourproject.repository.RecordRepository;
import org.yourcompany.yourproject.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final ExerciseRepository exerciseRepository;
    private final RecordRepository recordRepository;

    /**
     * 의사코드: 맴버 메인 라우팅 비즈니스 데이터 총합 연산부 구현
     */
    @Transactional(readOnly = true)
    public MemberTodayDashboardResDto getTodayDashboard(String memberId, LocalDate today) {
        // 1. 오늘의 운동 할당량 조회 (의사코드: todayassige 매핑)
        Exercise exercise = exerciseRepository.findByMember_UserIdAndTargetDate(memberId, today).orElse(null);

        // 2. 오늘의 권장 식단 가이드라인 조회
        Meal meal = mealRepository.findByMember_UserIdAndTargetDate(memberId, today).orElse(null);

        // 3. 의사코드 whlie(끝까지 조회) 조건절 대체 -> 금일 날짜에 겹치는 식단 테이블 리스트 초고속 쿼리
        List<Record> todayRecords = recordRepository.findByMember_UserIdAndRecordDate(memberId, today);

        // 4. 의사코드: [총칼로리/탄단지 비교기능] -> 스트림 루프 돌며 오늘 먹은 실제 총합 합산 연산
        int currentCal = todayRecords.stream().mapToInt(Record::getCalories).sum();
        int currentCarbs = todayRecords.stream().mapToInt(Record::getCarbo).sum();
        int currentProtein = todayRecords.stream().mapToInt(Record::getProtein).sum();
        int currentFat = todayRecords.stream().mapToInt(Record::getFat).sum();

        // 5. 대시보드 화면용 단일 반응 DTO로 가공 조립 후 리턴
        return MemberTodayDashboardResDto.builder()
                .assignId(exercise != null ? exercise.getAssignId() : null)
                .targetExerciseName(exercise != null ? exercise.getTargetExercise() : "할당된 운동이 없습니다.")
                .targetBurnedCalories(exercise != null ? exercise.getTargetBurnedCal() : 0)
                .isExerciseGoalDone(exercise != null && exercise.isTodoGoal())
                .targetCalories(meal != null ? meal.getTargetCalories() : 0)
                .targetCarbs(meal != null ? meal.getTargetCarbs() : 0)
                .targetProtein(meal != null ? meal.getTargetProtein() : 0)
                .targetFat(meal != null ? meal.getTargetFat() : 0)
                .currentTotalCalories(currentCal)
                .currentTotalCarbs(currentCarbs)
                .currentTotalProtein(currentProtein)
                .currentTotalFat(currentFat)
                .todayMealRecords(todayRecords) // 하단 타임라인 html 렌더링용 리스트
                .build();
    }

    /**
     * 의사코드: 클레스[운동완료체크기능] (운동 테이블 todogool -> not 변경)
     */
    @Transactional
    public void toggleExerciseStatus(ToggleExerciseGoalReqDto dto) {
        Exercise exercise = exerciseRepository.findById(dto.getAssignId())
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
        
        // 의사코드의 -> not 변경 (상태 반전 로직 적용)
        exercise.toggleGoalStatus(); 
    }

    /**
     * 의사코드: 클래스[식단 기록하기](기록 테이블 전달 -> 튜플 추가)
     */
    @Transactional
    public void addDietRecord(String memberId, AddRecordReqDto dto) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // DTO 상자 열어서 고유 식별 번호 로직(JPA save 자동화) 형태로 테이블 전달
        Record record = Record.builder()
                .member(member)
                .mealType(dto.getMealType())
                .mealTime(dto.getMealTime())
                .foodName(dto.getFoodName())
                .carbo(dto.getCarbo())
                .protein(dto.getProtein())
                .fat(dto.getFat())
                .calories(dto.getCalories())
                .recordDate(LocalDate.now()) // 오늘 날짜 자동 스탬프
                .recordDateTime(LocalDateTime.now())
                .build();

        recordRepository.save(record);
    }
}