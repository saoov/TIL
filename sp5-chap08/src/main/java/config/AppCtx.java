package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.ChangePasswordService;
import spring.MemberDao;

@Configuration
@EnableTransactionManagement
public class AppCtx {

	/*
	 * destroyMethod 속성값을 close로 설정한다.
	 * close 메서드는 커넥션 풀에 보관된 Connection을 닫는다.
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		ds.setInitialSize(2); //성능을 증가시키려는 이유로 커넥션풀을 초기화할 때 최소 수준의 커넥션을 미리 생성하는 것이 좋음.
		ds.setMaxActive(10); //활성상태 가능한 최대 커넥션 개수를 지정
		/*
		 * 커넥션의 연결이 끊어졌지만 여전히 풀속에 남아있을 수 있기 때문에 이런경우
		 * 해당 커넥션을 풀에서 가져와 사용하면 연결이 끊어진 커넥션이므로 익셉션이 발생한다.
		 * 따라서 아래 설정을 통해 커넥션 풀의 커넥션이 유효한지 주기적으로 검사할 수 있다.
		 */
		ds.setTestWhileIdle(true); //유휴 커넥션 검사
		ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3); //최소 유휴 시간 3분
		ds.setTimeBetweenEvictionRunsMillis(1000 * 10); //10초 주기
		return ds;
	}
	/*
	 * 스프링이 제공하는 트랜잭션 매니저 인터페이스로 구현기술에 상관없이 동일한 방식으로 트랜잭션 처리를 위해 사용
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager(); //트랜잭션 관리자
		tm.setDataSource(dataSource());
		return tm;
	}
	
	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource());
	}
	
	@Bean
	public ChangePasswordService changePwdSvc() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		pwdSvc.setMemberDao(memberDao());
		return pwdSvc;
	}
}
