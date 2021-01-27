package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;

/*
 * @RestController를 붙인 경우 스프링 MVC는 요청 매핑 애노테이션 붙은 메서드가 리턴한 객체를 알맞은 형식으로 변환해서 응답 데이터로 전송
 * 이때 클래스 패스에 Jackson이 존재하면 JSON형식의 문자열로 변환해서 응답한다.
 * 아래 members()메서드의 경우 리턴타입 List<Member>인 List객체를 JSON형식의 배열로 변환해서 응답한다.
 */
@RestController
public class RestMemberController {

	private MemberDao memberDao;
	private MemberRegisterService registerService;
	
	@GetMapping("/api/members")
	public List<Member> members(){
		return memberDao.selectAll();
	}
	
	@GetMapping("/api/members/{id}")
	public Member member(@PathVariable Long id, HttpServletResponse response) throws IOException{
		Member member = memberDao.selectById(id);
		if(member == null) {
			throw new MemberNotFoundException();
		}
		return member;
	}
	
	
//	@PostMapping("/api/members")
//	public void newMember(@RequestBody @Valid RegisterRequest regReq, HttpServletResponse response) throws IOException{
//		try {
//			Long newMemberId = registerService.regist(regReq);
//			response.setHeader("Location", "/api/members/" + newMemberId);
//			response.setStatus(HttpServletResponse.SC_CREATED); //회원가입 정상처리시 응답 코드로 201(CREATED)전송
//		} catch(DuplicateMemberException dupEx) {
//			response.sendError(HttpServletResponse.SC_CONFLICT); //중복된 ID를 전송한 경우 응답 상태 코드로 409(CONFLICT)를 리턴
//		}
//	}
	
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public void setRegisterService(MemberRegisterService registerService) {
		this.registerService = registerService;
	}
}
