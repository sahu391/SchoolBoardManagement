package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
