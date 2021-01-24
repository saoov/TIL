package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @EnableWebMvc설정이 다양한 빈 설정을 추가해준다.
 * WebMvcConfigurer 인터페이스는 스프링 MVC의 개별 설정을 조정할 때 사용한다.
 */

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	
	
	/*
	 * DispatcherServlet의 매핑 경로를 '/'로 주었을 때 JSP/HTML/CSS를 처리하기 위한 설정
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	
	/*
	 * JSP를 이용해서 컨트롤러의 실행 결과를 보여주기 위한 설정
	 * 이 설정을 통해 hello()메서드가 리턴한 뷰 이름과 jsp파일의 연결이 이루어진다.
	 * registry.jsp()는 JSP를 뷰 구현으로 사용할 수 있도록 해주는 설정.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/",".jsp");
	}
	
	
}
