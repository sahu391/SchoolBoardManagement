package com.school.sba.requestdto;

import com.school.sba.enums.UserRole;

import jakarta.validation.constraints.Pattern;
public class UserRequest {
	
	@Pattern(regexp="^[a-zA-Z0-9]+$",message="username must" 
            +"contain altleat 8 t0 20 characters with no specialcharacters")
	private String userName;
    private String password;
	private String firstName;
	private String lastName;
	private long contactNo;
	private String email;
	private UserRole userRole;
	
	
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

    public UserRequest(
			@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "username mustcontain altleat 8 t0 20 characters with no specialcharacters") String userName,
			String password, String firstName, String lastName, long contactNo, String email, UserRole userRole) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.email = email;
		this.userRole = userRole;
	}
    
    
    public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
}
