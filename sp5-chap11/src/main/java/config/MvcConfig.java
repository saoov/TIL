package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	
	


	
}
