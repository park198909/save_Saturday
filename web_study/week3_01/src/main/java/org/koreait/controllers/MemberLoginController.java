package org.koreait.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.koreait.models.member.MemberLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member/login")
public class MemberLoginController {
	
	private MemberLoginValidator validator;
	private MemberLoginService service;
	
	public MemberLoginController(MemberLoginValidator validator, MemberLoginService service) {
		this.validator = validator;
		this.service = service;
	}
	
	@GetMapping
	public String login(Model model, @CookieValue(name="saveId", required=false) String sId) {
		System.out.println(sId);
		
		MemberLogin memberLogin = new MemberLogin();
		if (sId != null) { // 쿠키에 saveId  값이 존재하면 
			memberLogin.setSaveId(true); // 체크
			memberLogin.setUserId(sId); // 값 
		}
		
		model.addAttribute("memberLogin", memberLogin);
		
		return "member/login";
	}
	
	@PostMapping
	public String loginPs(@Valid MemberLogin memberLogin, Errors errors) { 
		validator.validate(memberLogin, errors);
		
		if (errors.hasErrors()) {
			return "member/login";
		}
		
		// 로그인 처리 
		service.login(memberLogin);
		
		return "redirect:/";  // HttpServletResponse sendRedirect
	}
}
