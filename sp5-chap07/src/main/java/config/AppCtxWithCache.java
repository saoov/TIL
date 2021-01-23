package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import aspect.CacheAspect;
import aspect.ExeTimeAspect;
import chap07.Calculator;
import chap07.RecCalculator;

@Configuration
@EnableAspectJAutoProxy
public class AppCtxWithCache {
	
	@Bean
	public CacheAspect cacheAspect() {
		return new CacheAspect();
	}
	
	/*
	 * 이게 왜 있어야 하는지 모르겠음 ㅠㅠ 그니까 왜 CacheAspect의 대상객체가 ExeTimeAspect인지 모름;
	 * ->없으면 시간 찍는 로깅이 안됨 
	 * ->그래도 모르겠음 ㅠㅠ
	 */
	@Bean
	public ExeTimeAspect exeTimeAspect() {
		return new ExeTimeAspect();
	}
	
	@Bean
	public Calculator calculator() {
		return new RecCalculator();
	}
}
