package org.yourcompany.yourproject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yourcompany.yourproject.dto.request.TotalTargetAssignReqDto;
import org.yourcompany.yourproject.dto.request.UpdatePtCountReqDto;
import org.yourcompany.yourproject.dto.response.CalendarDetailRecordDto;
import org.yourcompany.yourproject.dto.response.CalendarBriefInformationDto;
import org.yourcompany.yourproject.dto.response.LoginResDto;
import org.yourcompany.yourproject.dto.response.TrainerMemberListResDto;
import org.yourcompany.yourproject.entity.Meal;
import org.yourcompany.yourproject.entity.User;
import org.yourcompany.yourproject.repository.UserRepository;
import org.yourcompany.yourproject.service.TrainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.yourcompany.yourproject.repository.ExerciseRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;

    // 트레이너 메인 라우팅
    @GetMapping("/members")
    public String memberList(HttpSession session, Model model) {
        // 세션에서 로그인된 트레이너 ID 추출
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"TRAINER".equals(loginUser.getRole())) return "redirect:/login";

        // 트레이너 담당 리스트 조회
        List<TrainerMemberListResDto> chargeList = trainerService.getMemberList(loginUser.getUserId());
        model.addAttribute("chargeList", chargeList);

        return "trainer/list"; // templates/trainer/list.html (트레이너 메인창)
    }

    // 회원 선택
    @GetMapping("/member/{memberId}")
    public String memberDetail(@PathVariable String memberId, 
                               @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               Model model) {
        
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        
        if (date == null) date = LocalDate.now();

        LocalDate firstDayOfMonth = date.withDayOfMonth(1); 
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7; // 시작 요일 구하기
        int daysInMonth = date.lengthOfMonth(); // 해당 월의 총 일수
        model.addAttribute("startDayOfWeek", startDayOfWeek);
        model.addAttribute("daysInMonth", daysInMonth);

        Map<Integer, CalendarBriefInformationDto> todoDayMap = trainerService.getCalendarSummary(memberId, date);
        
        model.addAttribute("pageUserId", memberId);
        model.addAttribute("pageUserName", member.getName());
        model.addAttribute("ptCount", member.getPtCount()); 
        model.addAttribute("todoDay", todoDayMap);
        model.addAttribute("selectedDate", date);

        org.yourcompany.yourproject.entity.Exercise todayExercise = exerciseRepository.findByMember_UserIdAndTargetDate(memberId, date).orElse(null);
        model.addAttribute("todayExercise", todayExercise);

        // 식단 기록 및 목표 달성 여부 데이터
        List<CalendarDetailRecordDto> mealDay = trainerService.getDailyDietDetails(memberId, date);
        Meal targetMeal = trainerService.getDailyMealTarget(memberId, date);

        // 총 섭취량 자동 계산
        int totalKcal = mealDay.stream().mapToInt(m -> m.getCalories()).sum();
        int totalCarbs = mealDay.stream().mapToInt(m -> m.getCarbs()).sum();
        int totalProtein = mealDay.stream().mapToInt(m -> m.getProtein()).sum();
        int totalFat = mealDay.stream().mapToInt(m -> m.getFat()).sum();

        model.addAttribute("mealDay", mealDay);
        model.addAttribute("targetMeal", targetMeal);
        model.addAttribute("totalKcal", totalKcal);
        model.addAttribute("totalCarbs", totalCarbs);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalFat", totalFat);
        
        return "trainer/detail";
    }

    // PT 횟수 변경
    @PostMapping("/member/update-pt")
    @ResponseBody
    public void updatePtCount(@RequestBody UpdatePtCountReqDto ptCountDto) {
        trainerService.updatePtCount(ptCountDto);
    }

    // 목표 설정
    @PostMapping("/member/assign-target")
    public String assignTarget(@ModelAttribute TotalTargetAssignReqDto targetDto) {
        trainerService.assignTodayTarget(targetDto);
        return "redirect:/trainer/member/" + targetDto.getMemberId() + "?date=" + targetDto.getTargetDate();
    }

    // 회원 삭제
    @PostMapping("/member/remove")
    @ResponseBody
    public String deleteMember(@RequestParam String memberId) {
        trainerService.removeMemberFromTrainer(memberId);
        return "redirect:/trainer/members";
    }

    @PostMapping("/member/add")
    public String addMemberToTrainer(@RequestParam String userId, HttpSession session) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        trainerService.assignMemberToTrainer(loginUser.getUserId(), userId); 
        return "redirect:/trainer/members";
    }
}