package controller;

/*
 * 비밀번호 변경시 현재 비밀번호와 새 비밀번호를 입력받을 객체
 */
public class ChangePwdCommand {

	private String currentPassword;
	private String newPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
