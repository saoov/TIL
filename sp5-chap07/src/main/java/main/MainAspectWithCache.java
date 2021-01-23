package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import chap07.Calculator;
import config.AppCtxWithCache;

public class MainAspectWithCache {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithCache.class);
		
		/*
		 * calculator빈은 CacheAspect 프록시 객체
		 * CacheAspect 프록시 객체의 대상 객체는 ExeTimeAsepct의 프록시 객체
		 * ExetimeAspect프록시의 대상 객체가 실제 대상 객체
		 * 
		 * CacheAspect는 cache 맵에 데이터가 존재하지 않으면 joinPoint.process()를 실행해 대상을 실행한다.
		 * 그 대상이 ExeTimeAspect이므로 ExeTimeAspect의 measure()메서드가 실행된다.
		 */
		Calculator cal = ctx.getBean("calculator", Calculator.class);
		cal.factorial(7);
		cal.factorial(7);
		cal.factorial(5);
		cal.factorial(5);
		ctx.close();
	}
}

