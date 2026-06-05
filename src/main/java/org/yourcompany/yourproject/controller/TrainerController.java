package org.yourcompany.yourproject.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yourcompany.yourproject.dto.request.TotalTargetAssignReqDto;
import org.yourcompany.yourproject.dto.request.UpdatePtCountReqDto;
import org.yourcompany.yourproject.dto.response.CalendarBriefInformationDto;
import org.yourcompany.yourproject.dto.response.CalendarDetailRecordDto;
import org.yourcompany.yourproject.dto.response.LoginResDto;
import org.yourcompany.yourproject.dto.response.TrainerMemberListResDto;
import org.yourcompany.yourproject.service.TrainerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerService trainerService;

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
    @GetMapping("/member/{memberId}")
    public String memberCalendar(@PathVariable String memberId, 
                                 @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 HttpSession session, Model model) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        if (loginUser == null || !"TRAINER".equals(loginUser.getRole())) return "redirect:/login";

        if (date == null) {
            date = LocalDate.now(); // 날짜 선택이 안 되었으면 금일 기준
        }

        // 의사코드: Map<Integer, TodoDayDto> 형태를 보완한 캘린더 요약 맵 조회 (Key: 일자, Value: 달성요약)
        Map<Integer, CalendarBriefInformationDto> todoDayMap = trainerService.getCalendarSummary(memberId, date);
        
        // 의사코드: List<MealDetailDto> 패턴의 특정 일자 상세 식단 기록 리스트 조회
        List<CalendarDetailRecordDto> mealDayList = trainerService.getDailyDietDetails(memberId, date);

        model.addAttribute("pageUserId", memberId);
        model.addAttribute("todoDay", todoDayMap);
        model.addAttribute("mealDay", mealDayList);
        model.addAttribute("selectedDate", date);

        return "trainer/detail"; // templates/trainer/detail.html (회원 달력 화면)
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
    @PostMapping("/member/delete")
    public String deleteMember(@RequestParam String pageUserId, HttpSession session) {
        LoginResDto loginUser = (LoginResDto) session.getAttribute("loginUser");
        trainerService.removeMemberFromTrainer(pageUserId);
        return "redirect:/trainer/members";
    }
}