package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {

	private MemberDao memberDao;

	
	/*
	 * changePassword와 selectByEamil은 한 트랙잭션에 묶인다
	 * 이 애노테이션의 정상작동을 위해서는 
	 * 1. 플랫폼 트랜잭션 매니저 빈 설정
	 * 2. @Transactional 애노테이션 활성화 설정이 필요
	 */
	@Transactional
	public void changePassword(String email, String oldPwd, String newPwd) {
		Member member = memberDao.selectByEmail(email);
		if (member == null)
			throw new MemberNotFoundException();

		member.changePassword(oldPwd, newPwd);

		memberDao.update(member);
	}
	
	/*
	 * Transactional을 처리하기 위한 프록시 객체는 원본 객체의 메서드를 실행과정에서 RuntimeException이 발생하면
	 * 트랙잭션을 롤백한다. 별도의 추가설정이 없으면 발생한 익셉션이 RuntimeException일 때 롤백한다.
	 * WrongIdPasswordException 클래스 구현시 RuntimeException을 상속한 이유가 이것이다.
	 * SQLException은 RuntimeException을 상속하지 않으므로 트랜잭션을 롤백하지 않는다.
	 * 이때에도 트랜잭션을 롤백하고 싶다면 Transacitonal의 rollbackFor속성을 사용해야한다.
	 * @Transactional(rollbackFor = SQLException.class)
	 */

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

}
