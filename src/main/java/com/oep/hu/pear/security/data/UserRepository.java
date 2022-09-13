package com.oep.hu.pear.security.data;

import com.oep.hu.pear.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByEmail(String email);

	boolean existsByRoles_Name(String name);




}
