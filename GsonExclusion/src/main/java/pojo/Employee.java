package pojo;

import annotation.CustomExclude;

public class Employee {

	private String userName;
	private String email;

	@CustomExclude
	private String code;

	public Employee(String userName, String email, String code) {
		super();
		this.userName = userName;
		this.email = email;
		this.code = code;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
