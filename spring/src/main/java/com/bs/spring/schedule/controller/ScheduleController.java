package com.bs.spring.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.spring.schedule.model.dto.Schedule;
import com.bs.spring.schedule.model.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService service;

	//페이지 전환(일정관리)
	@GetMapping("/calendar")
	public String calendar() {
		return "calendar/calendar";
	}

    @PostMapping("/insertSchedule")
    @ResponseBody
    public String insertSchedule(@RequestBody Schedule schedule,
    		Model m) {
        int result = service.insertSchedule(schedule);
		if(result > 0) {
			return "redirect:/memo/memolist";
		}else {
			m.addAttribute("msg", "메모 저장 실패"); 
			m.addAttribute("loc", "/memo/memolist"); 
			return "common/msg";
		}
    }

    
}
