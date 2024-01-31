package com.school.sba.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.Entity.User;
import com.school.sba.enums.UserRole;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	public boolean existsByUserRole(UserRole userrole);

	Optional<User> findByUserName(String username);

	User findByUserRole(UserRole userRole);
	
	List<User> findByProgProgramIdAndUserRole(int programId, UserRole userRole);
	
	
}
