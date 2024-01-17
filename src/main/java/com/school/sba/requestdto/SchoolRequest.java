package com.school.sba.requestdto;

import com.school.sba.Entity.School;

import jakarta.persistence.ManyToOne;

public class SchoolRequest {
	
	private String schoolName;
	private long contactNo;
	private String emailId;
	private String address;
	
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
	public SchoolRequest(String schoolName, long contactNo, String emailId, String address) {
		super();
		
		this.schoolName = schoolName;
		this.contactNo = contactNo;
		this.emailId = emailId;
		this.address = address;
	}
	public SchoolRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
