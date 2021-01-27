package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import spring.RegisterRequest;

/*
 * RegisterRequest타입만 검증할 수 있으므로 모든 컨틀롤러에 적용할 수 있는 글로벌 범위 Validator로 적합하지 않다.
 */
public class RegisterRequestValidator implements Validator{
	private static final String emailRegExp = 
			"^[_A-Za-z0-0-\\+]+(\\.[_A-Zaa-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	
	public RegisterRequestValidator() {
		pattern = Pattern.compile(emailRegExp);
	}
	
	/*
	 * 파라미터로 전달받은 clazz객체가 RegisterRequest클래스로 타입 변환 가능한지 확인한다.
	 * 여기서 우리가 supports()메서드를 직접 실행하지 않지만 스프링 MVC가 자동으로 검증 기능을 수행하도록 하려면 supports()를 올바르게 구현해야 한다.
	 */

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterRequest.class.isAssignableFrom(clazz);
	}

	/*
	 * 첫번째 파라미터는 검사 대상 객체이고 errors파라미터는 검사 결과 에러 코드를 설정하기위한 객체이다.
	 * 올바른지 검사->올바르지 않다면 Errors의 rejectvalue()를 이용해 에러코드 저장
	 */
	
	@Override
	public void validate(Object target, Errors errors) {
		RegisterRequest regReq = (RegisterRequest)target; //전달받은 타겟을 실제 타입으로 변환
		if(regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) { //검사
			errors.rejectValue("email", "required"); //null이나 비었을 경우 email프로퍼티의 에러코드로 required추가
		} else {
			Matcher matcher = pattern.matcher(regReq.getEmail());
			if(!matcher.matches()) {
				errors.rejectValue("email", "bad");
			}
		}
		/*
		 * ValidationUtils 클래스는 객체 값 검증코드를 간결하게 작성하도록 도와줌  -> name검증 코드는 위 email검증 코드와 유사하지만 쉽게 작성되었다.
		 * name프로퍼티가 빈문자열이거나 공백문자로만 되어있는 경우 에러코드로 required 추가
		 * 검사 대상 객체 targert을 파라미터로 전달하지 않았는데 어떻게 target객체의 name프로퍼티의 값을 검사할까?
		 * ->요청 매핑 애노테이션 적용 메서드에 Errors타입 파라미터를 전달받고 이 Errors객체를 validate()메서드에 두번째 파라미터로 전달한다.
		 * errors의 getfiledValue()가 특정 프로퍼티 값을 구할 수 있다. 
		 * 
		 */
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
		if(!regReq.getPassword().isEmpty()) {
			if(!regReq.isPasswordEqualToConfirmPassword()) {
				errors.rejectValue("confirmPassword", "nomatch");
			}
		}
	}

}
