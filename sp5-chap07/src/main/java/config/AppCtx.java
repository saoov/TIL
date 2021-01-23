package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aspect.ExeTimeAspect;
import chap07.Calculator;
import chap07.RecCalculator;

/*
 * Enable~:프록시 생성과 관련된 AnnotaionAwareAspectJAutoProxyCreator객체를 빈으로 등록
 * 대상 객체가 한 개의 인터페이스를 구현하면 JDK 동적 Proxy 사용할 수 있다.(Spring AOP에서 선호)
 * proxyTargetClass=true 인터페이스 아닌 자바 클래스에서 상속방아 프록시 생성(CBLIB)
 * ->따라서 클래스가 final인 경우 프록시 생성할 수 없다.
 * */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true) 
public class AppCtx {
	@Bean
	public ExeTimeAspect exetimeAspect() {
		return new ExeTimeAspect();
	}
	
	/*
	 * AOP적용시 RecCalculator가 상속받은 
	 * Calculator 인터페이스를 이용해서 프록시 생성
	 * "calculator" 빈의 실제타입은 Calculator를 상속한 프록시타입이므로
	 * RecCalculator로 타입변환 할 수 없기 때문에 MainAspect에서
	 * RecCalculator로 받을 경우 익셉션 발생
	 */
	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}
}
