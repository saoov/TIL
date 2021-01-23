package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.Client;

@Configuration
public class AppCtx {

	//빈 객체 초기화와 소멸 시점에 InitializingBean, DisposableBean를 구현한 afterPropertiesSet()과 destroy()사용
	@Bean
	public Client client() {
		Client client = new Client();
		client.setHost("host");
		return client;
	}
	
//	//커스텀 메서드를 이용해 직접 구현한 메서드 사용 가능 -> 이 두 속성에 지정한 메서드는 파라미터가 없어야함 -> 있으면 익셉션 발생
//	@Bean(initMethod = "connect", destroyMethod="close")
//	public Client2 client2() {
//		Client2 client2 = new Client2();
//		client2.setHost("host");
//		return client2;
//	}
}
