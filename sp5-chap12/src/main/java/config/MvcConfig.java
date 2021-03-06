package config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import controller.RegisterRequestValidator;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/",".jsp");
	}

	
	/*
	 * step3는 단순히 회원가입 완료 후 첫 화면으로 이동할 수 있는 링크만 보여주기 때문에
	 * 특별한 로직이 없는 컨트롤러 클래스를 만드는 것은 불편하다
	 * 따라서 컨트롤러 구현 없이 요청 경로와 뷰 이름을 연결한다.
	 * /main 요청 경로에 대해 뷰 이름으로 main을 사용한다고 설정. ->main.jsp 파일을 만들어준다.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/main").setViewName("main");
	}
	
	/*
	 * 이 Bean은 빈의 아이디를 messageSource()로 작성하지 않으면 정상작동하지 않는다.
	 * <spring:message code=""/>태그는 내부적으로 MessageSource의 getMessage() 메서드를 실행해서 필요한 메시지를 구한다.
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource ms = 
				new ResourceBundleMessageSource();
		ms.setBasenames("message.label"); //message패키지에 속한 label프로퍼티를 읽어옴
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}
	
//	/*
//	 * 글로벌 범위 Validator 설정을 위한 getValidator()구현
//	 * 스프링 MVC는 getValidator()가 리턴한 객체를 글로벌 범위 Validator로 사용한다.
//	 * ->@Valid를 사용해서 Validator를 적용할 수 있다.
//	 */
//	@Override
//	public Validator getValidator() {
//		return new RegisterRequestValidator();
//	}
	


	
}
