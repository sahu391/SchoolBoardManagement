package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.Entity.User;
import com.school.sba.enums.UserRole;

public interface UserRepository extends JpaRepository<User, Integer>{

	public boolean existsByUserRole(UserRole userrole);
}
