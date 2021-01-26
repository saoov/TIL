package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

@Controller
@RequestMapping("/login")
public class LoginController {
	private AuthService authService;
	
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	
	/*
	 * @CookieValue는 요청 매핑 애노테이션 적용 메서드의 cookie타입파라미터에 적용한다.
	 * 이름이 REMEMBER인 쿠키를 Cookie타입으로 전달받는다. 존재하지 않을수도 잇으므로 required=false.(기본값 true일 경우 쿠키가 없으면 익셉션 발생)
	 * 쿠키가 존재하면 쿠키 값을 읽어와 커맨드 객체의 email 프로퍼티에 값 설정->커맨드객체로 폼을 출력하므로 입력 폼의 email프로퍼티에 쿠키값이 채워져 출력된다.
	 */
	@GetMapping
	public String form(LoginCommand loginCommand,@CookieValue(value="REMEMBER", required = false) Cookie rCookie) {
			if(rCookie != null) {
				loginCommand.setEmail(rCookie.getValue());
				loginCommand.setRememberEmail(true);
			}
			return "login/loginForm";
		}

	
	/*
	 * 쿠키를 생성하려면 HttpServletResponse객체가 필요하므로 submit()의 파라미터로 HttpServletResponse타입을 추가한다.
	 */
	
	@PostMapping
	public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, HttpServletResponse response) {
		new LoginCommandValidator().validate(loginCommand, errors);
		if(errors.hasErrors()) {
			return "login/loginForm";
		}
		try {
			AuthInfo authInfo = authService.authenticate(loginCommand.getEmail(), loginCommand.getPassword());
			session.setAttribute("authInfo", authInfo);
			
			Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
			rememberCookie.setPath("/");
			if(loginCommand.isRememberEmail()) {
				rememberCookie.setMaxAge(60 * 60 * 24 * 30);
			} else {
				rememberCookie.setMaxAge(0); //쿠키를 삭제
			}
			response.addCookie(rememberCookie);
			
			return "login/loginSuccess";
		} catch (WrongIdPasswordException e) {
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}
}
