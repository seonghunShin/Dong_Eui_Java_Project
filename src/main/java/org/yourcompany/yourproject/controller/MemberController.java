package org.yourcompany.yourproject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
import org.yourcompany.yourproject.service.MemberService;
import org.yourcompany.yourproject.service.TrainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final TrainerService trainerService;

    // 의사코드: 맴버 메인 라우팅(userId) 통합 구현
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // 세션에서 현재 로그인한 회원 ID 추출
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"MEMBER".equals(loginUser.getRole())) return "redirect:/login";

        String pageUserId = loginUser.getUserId();
        LocalDate today = LocalDate.now(); // 금일 날짜 확인

        // 의사코드: [맴버메인창 구성기능] + [총칼로리/탄단지 비교기능] 연산 결과를 종합한 뼈대 DTO 조회
        MemberTodayDashboardResDto dashboardData = memberService.getTodayDashboard(pageUserId, today);
        
        // 통째로 모델에 담아 화면 단으로 전달 (todayAssige, todayRecord, 누적 칼로리 정보가 다 들어있음)
        model.addAttribute("dashboard", dashboardData);

        return "member/dashboard"; // templates/member/dashboard.html
    }

    // 의사코드: 클레스[운동완료체크기능] (운동 테이블 todogool -> not 변경)
    @PostMapping("/exercise/toggle")
    public String toggleExercise(@ModelAttribute ToggleExerciseGoalReqDto toggleDto) {
        memberService.toggleExerciseStatus(toggleDto);
        return "redirect:/member/dashboard";
    }

    // 의사코드: 클래스[식단 기록하기](기록 테이블 전달 -> 튜플 추가)
    @PostMapping("/diet/record")
    public String recordDiet(@ModelAttribute AddRecordReqDto recordDto, HttpSession session) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        // 회원의 식단 기록 추가 및 당일 비교검증 유틸 구동
        memberService.addDietRecord(loginUser.getUserId(), recordDto);
        
        return "redirect:/member/dashboard"; // 기록 후 대시보드로 갱신 이동
    }

    // 🎯 멤버의 내 목표 달력 보기 페이지로 이동
    // 🎯 멤버의 내 목표 달력 보기 페이지로 이동 (날짜 자동계산 버전)
    // 🎯 멤버의 내 목표 달력 보기 페이지로 이동
    @GetMapping("/calendar")
    public String myCalendarPage(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            HttpSession session, Model model) {
        
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"MEMBER".equals(loginUser.getRole())) {
            return "redirect:/login-page"; 
        }

        if (date == null) {
            date = LocalDate.now(); 
        }

        String memberId = loginUser.getUserId();

        Map<Integer, CalendarBriefInformationDto> todoDayMap = trainerService.getCalendarSummary(memberId, date);
        List<CalendarDetailRecordDto> mealDayList = trainerService.getDailyDietDetails(memberId, date);

        // 🎯 [신규 추가] 달력에서 클릭한 해당 날짜의 "운동 목표와 식단 달성 현황 상세 DTO"를 꺼내옵니다!
        MemberTodayDashboardResDto dailyDetail = memberService.getTodayDashboard(memberId, date);

        LocalDate firstDayOfMonth = date.withDayOfMonth(1); 
        int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7; 
        int daysInMonth = date.lengthOfMonth(); 

        model.addAttribute("todoDay", todoDayMap);
        model.addAttribute("mealDay", mealDayList);
        model.addAttribute("dailyDetail", dailyDetail); // 💡 타임리프로 넘겨줍니다!
        model.addAttribute("selectedDate", date);
        model.addAttribute("calUserName", loginUser.getName());
        model.addAttribute("startDayOfWeek", startDayOfWeek);
        model.addAttribute("daysInMonth", daysInMonth);

        return "member/memberdetail"; 
    }
}