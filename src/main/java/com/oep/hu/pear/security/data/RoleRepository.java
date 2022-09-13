package com.oep.hu.pear.security.data;

import com.oep.hu.pear.security.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
