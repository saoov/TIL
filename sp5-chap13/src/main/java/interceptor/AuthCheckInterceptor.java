package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/*
 * preHand()는 컨트롤러(핸들러)객체를 실행하기 전에 필요한 기능을 구현할 때 사용한다.
 * 로그인 여부에 따라 로그인 폼으로 보내거나 컨트롤러를 실행하도록 구현
 * preHandler()는 true를 리턴하면 컨트롤러(또는 다음 HandlerInterceptor)를 실행한다.
 */
public class AuthCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if(session != null) {
			Object authInfo = session.getAttribute("authInfo");
			if(authInfo != null) {
				return true;
			}
		}
		response.sendRedirect(request.getContextPath()+"/login");
		return false; //false를 리턴하면 로그인상태가 아니므로 지정한 경로로 리다이렉트한다. request.getContextPath()는 현재 컨텍스트 경로를 리턴한다.
	}
	
	/*
	 * HandlerInterceptor를 구현하면 어디에 적용해야 할지 설정해야 한다. -> MvcConfig설정 클래스에 추가
	 */
	
	
}
