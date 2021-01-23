package chap07;

public class ExeTimeCalculator implements Calculator {

	private Calculator delegate;
	
	public ExeTimeCalculator(Calculator delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public long factorial(long num) {
		long start = System.nanoTime();
		//부가적인 기능은 다른 객체에 실행을 위임
		long result = delegate.factorial(num);
		long end = System.nanoTime();
		//계산하는 주 기능 외에 시간을 측정하는 부가적인 기능을 실행 -> Proxy
		System.out.printf("%s.factorial(%d) 실행 시간 = %d\n",
				delegate.getClass().getSimpleName(),
				num,(end-start));
		return result;
	}

}
