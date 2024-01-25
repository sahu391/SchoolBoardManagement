package com.school.sba.Entity;

import java.util.ArrayList;
import java.util.List;

import com.school.sba.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(unique = true)
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	
	private Boolean isDeleted;
	
	@Column(unique = true)
	private String email;
	private UserRole userRole;
	
	@ManyToOne
	private School school;
	
	@ManyToMany
	private List<AcademicProgram> prog=new ArrayList<AcademicProgram>();
	
	@ManyToOne
	private Subject subject;
	
	
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public List<AcademicProgram> getProg() {
		return prog;
	}
	public void setProg(List<AcademicProgram> prog) {
		this.prog = prog;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public User(int userId, String userName, String password, String firstName, String lastName, long contactNo,
			Boolean isDeleted, String email, UserRole userRole, School school, List<AcademicProgram> prog,
			Subject subject) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.isDeleted = isDeleted;
		this.email = email;
		this.userRole = userRole;
		this.school = school;
		this.prog = prog;
		this.subject = subject;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
