package org.yourcompany.yourproject.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.yourcompany.yourproject.dto.request.AddRecordReqDto;
import org.yourcompany.yourproject.dto.request.ToggleExerciseGoalReqDto;
import org.yourcompany.yourproject.dto.response.CalendarBriefInformationDto;
import org.yourcompany.yourproject.dto.response.CalendarDetailRecordDto;
import org.yourcompany.yourproject.dto.response.LoginResDto;
import org.yourcompany.yourproject.dto.response.MemberTodayDashboardResDto;
import org.yourcompany.yourproject.entity.Exercise;
import org.yourcompany.yourproject.entity.Meal;
import org.yourcompany.yourproject.repository.ExerciseRepository;
import org.yourcompany.yourproject.repository.MealRepository;
import org.yourcompany.yourproject.service.MemberService;
import org.yourcompany.yourproject.service.TrainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final TrainerService trainerService;
    private final ExerciseRepository exerciseRepository;
    private final MealRepository mealRepository;

    // 💡 생성자를 직접 작성하여 컴파일러에게 명확하게 알려줍니다.
    public MemberController(MemberService memberService, TrainerService trainerService,
                            ExerciseRepository exerciseRepository, MealRepository mealRepository) {
        this.memberService = memberService;
        this.trainerService = trainerService;
        this.exerciseRepository = exerciseRepository;
        this.mealRepository = mealRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"MEMBER".equals(loginUser.getRole())) return "redirect:/login";

        String pageUserId = loginUser.getUserId();
        LocalDate today = LocalDate.now();

        MemberTodayDashboardResDto dashboardData = memberService.getTodayDashboard(pageUserId, today);
        model.addAttribute("dashboard", dashboardData);

        Exercise todayExercise = exerciseRepository.findByMember_UserIdAndTargetDate(pageUserId, today).orElse(null);
        model.addAttribute("todayExercise", todayExercise);

        Meal todayMeal = mealRepository.findByMember_UserIdAndTargetDate(pageUserId, today).orElse(null);
        model.addAttribute("todayMeal", todayMeal);

        int totalKcal = 0, totalCarbs = 0, totalProtein = 0, totalFat = 0;
        if (dashboardData.getTodayMealRecords() != null) {
            totalKcal = dashboardData.getTodayMealRecords().stream().mapToInt(m -> m.getCalories()).sum();
            totalCarbs = dashboardData.getTodayMealRecords().stream().mapToInt(m -> m.getCarbo()).sum();
            totalProtein = dashboardData.getTodayMealRecords().stream().mapToInt(m -> m.getProtein()).sum();
            totalFat = dashboardData.getTodayMealRecords().stream().mapToInt(m -> m.getFat()).sum();
        }
        model.addAttribute("totalKcal", totalKcal);
        model.addAttribute("totalCarbs", totalCarbs);
        model.addAttribute("totalProtein", totalProtein);
        model.addAttribute("totalFat", totalFat);

        return "member/dashboard";
    }

    @PostMapping("/exercise/toggle")
    public String toggleExercise(@ModelAttribute ToggleExerciseGoalReqDto toggleDto) {
        memberService.toggleExerciseStatus(toggleDto);
        return "redirect:/member/dashboard";
    }

    @PostMapping("/diet/record")
    public String recordDiet(@ModelAttribute AddRecordReqDto recordDto, HttpSession session) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        memberService.addDietRecord(loginUser.getUserId(), recordDto);
        return "redirect:/member/dashboard";
    }


    @GetMapping("/calendar")
    public String myCalendarPage(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            HttpSession session, Model model) {
        
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"MEMBER".equals(loginUser.getRole())) {
            return "redirect:/login-page"; 
        }
        if (date == null) date = LocalDate.now(); 

        String memberId = loginUser.getUserId();
        Map<Integer, CalendarBriefInformationDto> todoDayMap = trainerService.getCalendarSummary(memberId, date);
        List<CalendarDetailRecordDto> mealDayList = trainerService.getDailyDietDetails(memberId, date);
        MemberTodayDashboardResDto dailyDetail = memberService.getTodayDashboard(memberId, date);

        LocalDate firstDayOfMonth = date.withDayOfMonth(1); 
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7; 
        int daysInMonth = date.lengthOfMonth(); 

        model.addAttribute("todoDay", todoDayMap);
        model.addAttribute("mealDay", mealDayList);
        model.addAttribute("dailyDetail", dailyDetail);
        model.addAttribute("selectedDate", date);
        model.addAttribute("calUserName", loginUser.getName());
        model.addAttribute("startDayOfWeek", startDayOfWeek);
        model.addAttribute("daysInMonth", daysInMonth);

        return "member/memberdetail"; 
    }
}