package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

/*
 * RegisterController 클래스는 MemberRegisterService타입의 빈을 의존하므로
 * ControllerConfig파일에 의존 주입을 설정한다.
 */

@Controller
public class RegisterController {
	
	private MemberRegisterService memberRegisterService;

	public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
		this.memberRegisterService = memberRegisterService;
	}

	@RequestMapping("/register/step1")
	public String handleStep1() {
		return "register/step1";
	}
	/*
	 * 개수가 적고 간단한 요청 파라미터에 접근하는 방법 @RequestParam
	 * 스프링 MVC는 파라미터 타입에 맞게 String 값을 변환해준다. 아래의 경우 agree요청 파라미터 값을 읽어와
	 * Boolean 타입으로 변환해서 agree에 전달한다.
	 * checkbox타입의 input요소는 선택되지 않으면 파라미터로 아무 값도 전송하지 않는다. ->400 에러
	 * 또한 요청 파라미터 값을 애노테이션이 적용된 파라미터 타입으로 변환할 수 없는 경우도 에러가 발생한다.->400에러
	 * 위의 예로 value='true1'을 주면 true1을 boolean으로 변환할 수 없기 때문에 에러 발생
	 * 또한 커맨드 객체 프로퍼티 타입이 int인데 요청 파라미터값이 'abc'라면 에러가 발생한다.
	 * 따라서 이를 예방하기 위해 @RequestParam(value="agree", defaultValue="false")도 좋다.
	 */
	@PostMapping("/register/step2")
	public String handleStep2(@RequestParam("agree")Boolean agree, Model model) {
		if(!agree) {
			return "register/step1";
		}
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register/step2";
	}
	
	/*
	 * URL에 입력하는 방식->Get방식이므로 약관처리하는 step1로 갈수있게 redirect해준다.
	 * POST방식만 처리하는 메서드에 GET방식 요청이 들어올 경우 405에러 발생
	 * redirect:뒤의 문자열이'/'로 시작하면 웹 어플리케이션 기준으로 이동경로 생성한다.
	 * 아래의 경우  /sp5-chap11/register/step1가 경로가 됨
	 */
	@GetMapping("/register/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}
	
	
	/*
	 * 스프링이 RegisterRequest 클래스에 있는 set함수를 사용하여 요청 파라미터의 값을 커멘드 객체에 복사하고
	 * regReq파라미터로 전달한다. 요청파라미터를 받을 수 있는 setter를 포함한 객체를 커맨드 객체로 사용하면 된다.
	 * 
	 */
	@PostMapping("/register/step3")
	public String handleStep3(RegisterRequest regReq) {
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			return "register/step2";
		}
	}
}
