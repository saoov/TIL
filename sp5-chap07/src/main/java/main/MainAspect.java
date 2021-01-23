package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chap07.Calculator;
import config.AppCtx;

public class MainAspect {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppCtx.class);
		
		Calculator cal = ctx.getBean("calculator", Calculator.class);
		/*
		 * AOP가 아니었더라면 getBean이 리턴한 객체는 프록시가 아닌 RecCalculator타입이었을 것이다. 
		 * -> AppCtx의 Bean
		 */
		long fiveFact = cal.factorial(5);
		/*
		 * ExeTimeAspect
		 */
		System.out.println("cal.factorial(5) = "+fiveFact);
		System.out.println(cal.getClass().getName()); //com.sun.proxy.$Proxy17
		ctx.close();
	}
}
