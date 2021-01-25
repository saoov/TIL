package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	/*
	 * Model파라미터는 컨트롤러 처리 결과를 뷰에 전달할 때 사용한다.
	 * @RequestParam 애노테이션은 HTTP요청 파라미터의 값을 메서드의 파라미터로 전달할 때 사용한다. name 요청 파라미터의 값을 name 파라미터에 전달.
	 * 스프링 MVC 프레임워크가 여기서 모델에 추가한 attribute를 JSP에서 접근할 수 있게 HttpServletrequest에 옮겨준다.
	 * 컨트롤러의 실행 결과를 HandlerAdapter를 통해 ModelAndView형태로 받는다. 
	 * Model에 담긴 값은 View 객체에 Map형식으로 전달된다.
	 */
	
	
	@GetMapping("/hello")
	public String hello(Model model, @RequestParam(value = "name", required = false) String name) {
		model.addAttribute("greeting", "안녕하세요, " + name);
		return "hello";
	}
}
