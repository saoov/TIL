package controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.Member;
import spring.MemberDao;

/*
 * 스프링MVC는 요청 매핑 애노테이션 적용 메서드와 DispatcherServlet사이를 연결하기 위해 
 * RequestMappingHandlerAdapter객체를 사용한다. 이 핸들러 어댑터 객체는 요청 파라미터와
 * 커맨드 객체 사이의 변환 처리를 위해 WebDataBinder를 이용한다.
 * @EnableWebMvc를 사용하게 되면 WebDataBinder가 ConversionService에 역할을 위임해 @DateTimeFormat을 붙인 커맨드 객체의 시간관련 타입 변환 기능을 제공한다.
 */
@Controller
public class MemberListController {

	private MemberDao memberDao;
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping("/members")
	public String list(@ModelAttribute("cmd")ListCommand listCommand, Errors errors, Model model) {
		if(errors.hasErrors()) {
			return "member/memberList";
		}
		if(listCommand.getFrom() != null && listCommand.getTo() != null) {
			List<Member> members = memberDao.selectByRegdate(listCommand.getFrom(), listCommand.getTo());
			model.addAttribute("members",members);
		}
		return "member/memberList";
	}
}
