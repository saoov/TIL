package aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

@Aspect
public class CacheAspect {

	private Map<Long, Object> cache = new HashMap<>();
	
	@Pointcut("execution(public * chap07..*(long))")
	public void cacheTarget() {
	}
	
	@Around("cacheTarget()")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		Long num = (Long) joinPoint.getArgs()[0];
		
		/*
		 * 두번쨰 호출시 cache.containsKey(num)이 true를 리턴하므로 cache맵에 담긴 값을 리턴하고 끝난다. 
		 * 이경우 joinPoint.proceed()를 실행하지 않으므로
		 * ExeTimeAspect나 실제 객체가 실행되지 않는다.
		 */
		if(cache.containsKey(num)) {
			System.out.printf("CacheAspect : Cache에서 구함[%d]\n", num);
			return cache.get(num);
		}
		/*
		 * key값이 cache에 존재하지 않으면(최초호출) 프록시 대상 객체(ExeTimeAspect) 실행<--??
		 * 따라서 ExeTimeAspect의 measure()가 실행
		 */
		Object result = joinPoint.proceed();
		cache.put(num, result);
		System.out.printf("CacheAspect : Cache에 추가[%d]\n", num);
		return result;
	}
}
