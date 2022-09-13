package com.oep.hu.pear.security.application;

import com.oep.hu.pear.security.data.RoleRepository;
import com.oep.hu.pear.security.domain.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {

	private RoleRepository repository;

	public Role createRole(String name) {
		return this.repository.save(new Role(name));
	}

}
