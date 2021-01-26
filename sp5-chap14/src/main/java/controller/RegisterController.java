package controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
	 * 요청 매핑 어노테이션 적용 메서드의 커맨드 객체 파라미터 뒤에 Errors타입 파라미터가 위치하면 스프링 MVC는
	 * handleStep3()를 호출할 때 커맨드 객체와 연결된 Errors 객체를 생성해서 파라미터로 전달한다.
	 * Errors타입 파라미터는 반드시 커맨드 객체 파라미터 다음에 위치해야 한다.
	 * 이 객체는 커맨드 객체의 특정 프로퍼티 값을 구할 수 있는 getFieldValue()메서드를 제공한다.
	 * 따라서 ValidationUtils.rejectIfEmptyOrWhitespace()메서드는 커맨드 객체를 전달받지 않아도 Errors 객체를 이용해서 지정한 값을 구할 수 있다.
	 * 
	 * RegisterRequestValidator객체를 생성하고 validate()를 실행->검사하고 결과를 Errors객체에 담는다. ->@Valid이용시 글로벌 Validator
	 * 이 애노테이션은 Errors타입 파라미터가 없으면 검증실패시 400에러
	 */
	@PostMapping("/register/step3")
	public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {
//		new RegisterRequestValidator().validate(regReq, errors); 
		if(errors.hasErrors()) //에러 존재 검사
			return "register/step2";
		try {
			memberRegisterService.regist(regReq);
			return "register/step3";
		} catch (DuplicateMemberException ex) {
			errors.rejectValue("email","duplicate"); //validate()실행 중 유효하지 않은 값이 존재할 경우 rejectValue() 실행
			return "register/step2";
		}
	}
	/*
	 * @InitBinder를 통한 컨틀롤러 범위 Validator를 설정
	 * 어떤 Validator가 커맨드 객체를 검증할지는 initBinder()가 결정한다.
	 * @InitBinder를 적용한 메서드는 WebDataBinder 타입 파라미터를 갖는데 
	 * WebDataBinder.setValidator()를 통해 컨트롤러 범위에 적용할 Validator를 설정할 수 있다.
	 * ->RegisterRequestValidator()를 컨틀롤러 범위로 설정했으므로 위 @Valid를 붙인 RegisterRequest를 검증할 때 이를 사용한다.
	 */
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(new RegisterRequestValidator());
//	}
}
