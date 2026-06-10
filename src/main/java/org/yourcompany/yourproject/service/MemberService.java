package org.yourcompany.yourproject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final ExerciseRepository exerciseRepository;
    private final RecordRepository recordRepository;

    // 멤버 대시보드 조회
    @Transactional(readOnly = true)
    public MemberTodayDashboardResDto getTodayDashboard(String memberId, LocalDate today) {

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 오늘의 운동 할당량 조회
        Exercise exercise = exerciseRepository.findByMember_UserIdAndTargetDate(memberId, today).orElse(null);

        // 오늘의 권장 식단 가이드라인 조회
        Meal meal = mealRepository.findByMember_UserIdAndTargetDate(memberId, today).orElse(null);

        // 금일 날짜에 겹치는 식단 테이블 리스트 초고속 쿼리
        List<Record> todayRecords = recordRepository.findByMember_UserIdAndRecordDate(memberId, today);

        // 총칼로리, 탄단지 계산
        int currentCal = todayRecords.stream().mapToInt(Record::getCalories).sum();
        int currentCarbs = todayRecords.stream().mapToInt(Record::getCarbo).sum();
        int currentProtein = todayRecords.stream().mapToInt(Record::getProtein).sum();
        int currentFat = todayRecords.stream().mapToInt(Record::getFat).sum();

        // 대시보드 화면용 단일 반응 DTO로 가공 조립 후 리턴
        return MemberTodayDashboardResDto.builder()
                .name(member.getName())
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
                .todayMealRecords(todayRecords)
                .build();
    }

    // 운동완료 체크 기능
    @Transactional
    public void toggleExerciseStatus(ToggleExerciseGoalReqDto dto) {
        Exercise exercise = exerciseRepository.findById(dto.getAssignId())
                .orElseThrow(() -> new IllegalArgumentException("과제를 찾을 수 없습니다."));
        exercise.toggleGoalStatus(); 
    }

    // 식단 기록
    @Transactional
    public void addDietRecord(String memberId, AddRecordReqDto dto) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        Record record = Record.builder()
                .member(member)
                .mealType(dto.getMealType())
                .mealTime(dto.getMealTime())
                .foodName(dto.getFoodName())
                .carbo(dto.getCarbo())
                .protein(dto.getProtein())
                .fat(dto.getFat())
                .calories(dto.getCalories())
                .recordDate(LocalDate.now())
                .recordDateTime(LocalDateTime.now())
                .build();

        recordRepository.save(record);
    }
}