package org.yourcompany.yourproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yourcompany.yourproject.dto.request.TotalTargetAssignReqDto;
import org.yourcompany.yourproject.dto.request.UpdatePtCountReqDto;
import org.yourcompany.yourproject.dto.response.CalendarBriefInformationDto;
import org.yourcompany.yourproject.dto.response.CalendarDetailRecordDto;
import org.yourcompany.yourproject.dto.response.TrainerMemberListResDto;
import org.yourcompany.yourproject.entity.Exercise;
import org.yourcompany.yourproject.entity.Meal;
import org.yourcompany.yourproject.entity.Record;
import org.yourcompany.yourproject.entity.User;
import org.yourcompany.yourproject.repository.ExerciseRepository;
import org.yourcompany.yourproject.repository.MealRepository;
import org.yourcompany.yourproject.repository.RecordRepository;
import org.yourcompany.yourproject.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final ExerciseRepository exerciseRepository;
    private final RecordRepository recordRepository;

    /**
     * 의사코드: 클레스[트레이너 담당 리스트 불러오기] 구현
     */
    @Transactional(readOnly = true)
    public List<TrainerMemberListResDto> getMemberList(String trainerId) {
        // DB에서 trainerId가 일치하는 회원 목록을 한 번에 select 해옵니다.
        List<User> members = userRepository.findByTrainerId(trainerId);

        // 엔티티 리스트를 DTO 리스트로 깔끔하게 변환(Stream 활용)
        return members.stream()
                .map(m -> TrainerMemberListResDto.builder()
                        .memberId(m.getUserId())
                        .name(m.getName())
                        .ptCount(m.getPtCount())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 의사코드: Map<Integer, TodoDayDto> 형태의 달력 일별 요약 맵 생성 데이터 로직
     */
    @Transactional(readOnly = true)
    public Map<Integer, CalendarBriefInformationDto> getCalendarSummary(String memberId, LocalDate date) {
        Map<Integer, CalendarBriefInformationDto> summaryMap = new HashMap<>();

        // 특정 년/월의 1일부터 31일까지 데이터를 루프 돌며 가공 (여기서는 예시로 간소화 구현)
        int lengthOfMonth = date.lengthOfMonth();
        for (int day = 1; day <= lengthOfMonth; day++) {
            LocalDate targetDate = date.withDayOfMonth(day);

            // 해당 날짜의 운동/식단 가이드 조회
            Exercise ex = exerciseRepository.findByMember_UserIdAndTargetDate(memberId, targetDate).orElse(null);
            
            String goalStatus = (ex != null && ex.isTodoGoal()) ? "성공" : "진행중";
            int burnedCal = (ex != null) ? ex.getTargetBurnedCal() : 0;

            summaryMap.put(day, CalendarBriefInformationDto.builder()
                    .todoGoal(goalStatus)
                    .targetBurningCalories(burnedCal)
                    .recordDiet("조회필요") // 식단Validator와 연동하여 달성/초과 텍스트 매핑 가능
                    .build());
        }
        return summaryMap;
    }

    /**
     * 의사코드: String[][] mealDay 대용 List<MealDetailDto> 구현 (일별 식단 상세 타임라인)
     */
    @Transactional(readOnly = true)
    public List<CalendarDetailRecordDto> getDailyDietDetails(String memberId, LocalDate date) {
        List<Record> records = recordRepository.findByMember_UserIdAndRecordDate(memberId, date);

        return records.stream()
                .map(r -> CalendarDetailRecordDto.builder()
                        .recordId(r.getRecordId())
                        .memberId(r.getMember().getUserId())
                        .recordDate(r.getRecordDate())
                        .mealTime(r.getMealTime())
                        .mealType(r.getMealType())
                        .foodName(r.getFoodName())
                        .carbs(r.getCarbo())
                        .protein(r.getProtein())
                        .fat(r.getFat())
                        .calories(r.getCalories())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 의사코드: public void PT 횟수 변경(userId, newPTCount)
     */
    @Transactional
    public void updatePtCount(UpdatePtCountReqDto dto) {
        User member = userRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.updatePtCount(dto.getPtCount()); // 더티 체킹(Dirty Checking)으로 DB 자동 반영
    }

    /**
     * 의사코드: public void 목표설정(목표설정Dto, pageId)
     */
    @Transactional
    public void assignTodayTarget(TotalTargetAssignReqDto dto) {
        User member = userRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        // 1. 식단 과제 저장 또는 수정
        Meal meal = mealRepository.findByMember_UserIdAndTargetDate(dto.getMemberId(), dto.getTargetDate())
                .orElse(Meal.builder().member(member).targetDate(dto.getTargetDate()).build());
        meal.updateTargets(dto.getTargetCalories(), dto.getTargetCarbs(), dto.getTargetProtein(), dto.getTargetFat());
        mealRepository.save(meal);

        // 2. 운동 과제 저장 또는 수정
        Exercise exercise = exerciseRepository.findByMember_UserIdAndTargetDate(dto.getMemberId(), dto.getTargetDate())
                .orElse(Exercise.builder().member(member).targetDate(dto.getTargetDate()).build());
        exercise.updateExerciseTargets(dto.getTargetExerciseName(), dto.getTargetBurnedCalories());
        exerciseRepository.save(exercise);
    }

    /**
     * 의사코드: public void 회원삭제(pageUserId, trainerId) -> 외래키 해제 유연 로직
     */
    @Transactional
    public void removeMemberFromTrainer(String memberId) {
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.disconnectTrainer(); // 담당 트레이너 연관 관계 해제 (null 변경)
    }
}