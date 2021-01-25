package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * @EnableWebMvc설정이 다양한 빈 설정을 추가해준다.
 * ->핸들러에 알맞은 HandlerMapping(핸들러 객체를 찾기위해)빈과 HandlerAdapter(핸들러객체를 실행하고 ModelAndView 객체로 변환해서 DispatcherServlet에 리턴)빈을 스프링 설정에 등록해준다.
 * WebMvcConfigurer 인터페이스는 스프링 MVC의 개별 설정을 조정할 때 사용한다.
 */

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	
	
	/*
	 * DispatcherServlet의 매핑 경로를 '/'로 주었을 때 JSP/HTML/CSS를 처리하기 위한 설정
	 * DefaultServletHandlerConfigurer는 클라이언트의 모든 요청을 WAS가 제공하는 디폴트 서블릿에 전달한다.
	 * @enableWebMvc 애노테이션 등록 RequestMappingHandlerMapping의 적용 우선 순위가 enable()메서드가 등록하는 SimpleUrlHandlerMapping의 우선순위보다 높다 
	 * -> 웹 브라우저의 요청이 들어오면 DispatcherServlet은 RequestMappingHandlerMapping을 사용해 요청을 처리할 핸들러 검색하고 
	 * 존재하지 않으면 SimpleUrlHandlerMapping을 사용해 요청을 처리할 핸들러를 검색한다.
	 * 따라서 DefaultServletHandlerConfigurer의 enable()을 설정하면 별도 설정이 없는 모든 요청 경로를 디폴트 서블릿이 처리한다.
	 * 
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
		
		/*
		 * jsp()메서드를 이용해 JSP를 위한 ViewResolver를 설정
		 * 이렇게 할 경우 InternalResourceViewResolver 클래스를 이용해서 prefix와 suffix를 등록해준다.
		 * 요청 처리 결과를 생성할 View를 찾아주고 View는 최종적으로 클라이언트에 응답을 생성해서 전달한다.
		 */
	}
	
	
}
