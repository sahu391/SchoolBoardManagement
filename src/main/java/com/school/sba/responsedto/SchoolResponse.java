package com.school.sba.responsedto;

public class SchoolResponse {
	private int schoolId;
	private String schoolName;
	private long contactNo;
	private String emailId;
	private String address;
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public SchoolResponse(int schoolId, String schoolName, long contactNo, String emailId, String address) {
		super();
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.contactNo = contactNo;
		this.emailId = emailId;
		this.address = address;
	}
	public SchoolResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
