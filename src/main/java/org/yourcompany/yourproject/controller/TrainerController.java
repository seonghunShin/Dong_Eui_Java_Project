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

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final UserRepository userRepository;

    // 의사코드: 트레이너 메인 라우팅(userId)
    @GetMapping("/members")
    public String memberList(HttpSession session, Model model) {
        // 세션에서 로그인된 트레이너 ID 추출
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"TRAINER".equals(loginUser.getRole())) return "redirect:/login";

        // 의사코드: 클레스[트레이너 담당 리스트 불러오기] 기능 대응
        List<TrainerMemberListResDto> chargeList = trainerService.getMemberList(loginUser.getUserId());
        model.addAttribute("chargeList", chargeList);

        return "trainer/list"; // templates/trainer/list.html (트레이너 메인창)
    }

    // 의사코드: 클레스[회원 선택](pageUserId, chargeList.userId) -> 달력 화면 라우팅
    // 🎯 트레이너가 특정 회원의 상세 페이지로 들어올 때 (수정)
    @GetMapping("/member/{memberId}")
    public String memberDetail(@PathVariable String memberId, 
                               @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               Model model) {
        
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        
        if (date == null) date = LocalDate.now();

        Map<Integer, CalendarBriefInformationDto> todoDayMap = trainerService.getCalendarSummary(memberId, date);
        
        model.addAttribute("pageUserId", memberId);
        model.addAttribute("pageUserName", member.getName());
        model.addAttribute("ptCount", member.getPtCount()); 
        model.addAttribute("todoDay", todoDayMap);
        model.addAttribute("selectedDate", date);

        // 🎯 [신규 추가] 식단 기록 및 목표 달성 여부 데이터
        List<CalendarDetailRecordDto> mealDay = trainerService.getDailyDietDetails(memberId, date);
        Meal targetMeal = trainerService.getDailyMealTarget(memberId, date);

        // 총 섭취량 자동 계산 (스트림 활용)
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

    // 의사코드: public void PT 횟수 변경(userId, newPTCount)
    @PostMapping("/member/update-pt")
    @ResponseBody // 비동기 처리가 가능하도록 지정
    public void updatePtCount(@RequestBody UpdatePtCountReqDto ptCountDto) {
        trainerService.updatePtCount(ptCountDto);
    }

    // 의사코드: public void 목표설정(목표설정Dto, pageId)
    @PostMapping("/member/assign-target")
    public String assignTarget(@ModelAttribute TotalTargetAssignReqDto targetDto) {
        trainerService.assignTodayTarget(targetDto);
        return "redirect:/trainer/member/" + targetDto.getMemberId() + "?date=" + targetDto.getTargetDate();
    }

    // 의사코드: public void 회원삭제(pageUserId, trainerId)
    @PostMapping("/member/remove")
    @ResponseBody
    public String deleteMember(@RequestParam String memberId) {
        trainerService.removeMemberFromTrainer(memberId);
        return "redirect:/trainer/members";
    }

    @PostMapping("/member/add")
    public String addMemberToTrainer(@RequestParam String userId, HttpSession session) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        // 서비스에서 해당 userId의 회원 정보를 찾아 트레이너 ID를 update하는 로직 호출
        trainerService.assignMemberToTrainer(loginUser.getUserId(), userId); 
        return "redirect:/trainer/members";
    }
}