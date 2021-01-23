package aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;


//Aspect는 여러 객체에 공통적으로 적용되는 기능(트랜잭션,보안)을 말한다. Pointcut과 Advice를 합친것
@Aspect
public class ExeTimeAspect {
	/*공통 기능을 적용할 대상을 설정 
	 * execution 명시자 : 수식어패턴? 리턴타입패턴 클래스이름패턴?메서드이름패턴(파라미터패턴)
	 * public수식어, '모든 리턴타입'(*), 'chap07패키지와 그 하위 패키지에 위치'(..)한 모든(*) 메서드 중 파라미터 0개 이상(..)를 설정한다. 스프링 AOP는 public 메서드에만 적용 가능
	*
	*/
	@Pointcut("execution(public * chap07..*(..))")
	private void publicTarget() {
	}
	
	/*
	 * Around Advice를 설정. 
	 * publicTarget()메서드에 정의한 Pointcut에 공통 기능을 적용한다는 것.
	 */
	@Around("publicTarget()")
	public Object measure(ProceedingJoinPoint joinPoint) throws Throwable{ //ProceedingJoinPoint Proxy대상 객체의 메서드를 호출시 사용
		long start = System.nanoTime();
		try {
		Object result = joinPoint.proceed(); //AroundAdvice에서 사용할 공통 기능 메서드 호출방법
		return result;
		} finally {
		long finish = System.nanoTime();
		Signature sig = joinPoint.getSignature(); //getSignature 호출한 메서드의 시그니처(정보)(시그니처=메서드이름+파라미터)
		System.out.printf("%s.%s(%s)실행 시간 : %d ns\n",
				joinPoint.getTarget().getClass().getSimpleName(), //getTarget() 호출한 메서드의 대상객체
				sig.getName(), //시그니처 이름
				Arrays.toString(joinPoint.getArgs()), //getArgs() 호출한 메서드의 파라미터 목록
				(finish-start));
		}
	}
}
