package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AppContext.class); //Bean설정정보 읽어와 Greeter 객체 생성, 초기화
		Greeter g = ctx.getBean("greeter",Greeter.class); //getBean : 검색
		String msg = g.greet("스프링"); //Greeter객체의 greet()메서드를 실행
		System.out.println(msg);
		ctx.close();
	}
}
