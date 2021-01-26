package survey;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * form()과 submit()에는 전송방식만 설정하고 경로지정이 없다. 이는 두 메서드 처리 경로가 /survey가 된다.
 * 한 URL에 다른 처리방식 2가지를 갖는다.
 * URL을 통해 GET방식으로 들어온 요청에 대해 surveyForm을 반환하고 surveyForm의 post요청에 의해
 * submit()을 호출한다.
 */
@RequestMapping("/survey")
@Controller
public class SurveyController {

	@GetMapping
	public String form(Model model) {
		List<Question> questions = createQuestions();
		model.addAttribute("questions",questions);
		return "survey/surveyForm";
	}
	
	private List<Question> createQuestions(){
		Question q1 = new Question("당신의 역할을 무엇입니까?", Arrays.asList("서버","프론트","풀스택"));
		Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?", Arrays.asList("이클립스","인텔리제이","서브라임"));
		Question q3 = new Question("하고 싶은 말을 적어주세요");
		return Arrays.asList(q1,q2,q3);
	}
	/*
	 * submit()는 커맨드 객체로 AnsweredData(중첩프로퍼티를 갖는) 객체를 사용한다.
	 */
	@PostMapping
	public String submit(@ModelAttribute("ansData") AnsweredData data) {
		return "survey/submitted";
	}
}
