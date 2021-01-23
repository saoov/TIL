package spring;

public class Client2 {

	private String host;
	
	public void setHost(String host) {
		this.host = host;
	}
	
	//InitializingBean, DisposableBean를 구현한 afterPropertiesSet대신 사용
	public void connect() {
		System.out.println("Client2.connect() 실행");
	}
	
	public void send() {
		System.out.println("Client.send() to " +host);
	}

	
	//InitializingBean, DisposableBean를 구현한  destroy()대신에 사용
	public void close() {
		System.out.println("Client2.close() 실행");
	}
	
}
