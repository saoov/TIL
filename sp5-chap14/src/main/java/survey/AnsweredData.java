package survey;

import java.util.List;
/*
 * 설문 항목에 대한 답변(List<String>)과 응답자 정보(Respondent)가 담겨있는 클래스
 * 이 커맨드 객체는 중첩 프로퍼티를 갖는다. Respondent타입 res는 res.age와 res.location프로퍼티를 갖는다.
 */
public class AnsweredData {

	private List<String> responses;
	private Respondent res;

	public List<String> getResponses() {
		return responses;
	}

	public void setResponses(List<String> responses) {
		this.responses = responses;
	}

	public Respondent getRes() {
		return res;
	}

	public void setRes(Respondent res) {
		this.res = res;
	}
}
