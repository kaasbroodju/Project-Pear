package com.oep.hu.pear.security.application;

import com.oep.hu.pear.security.data.UserRepository;
import com.oep.hu.pear.security.domain.Role;
import com.oep.hu.pear.security.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = this.userRepository.findByEmail(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		} else {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
		}
	}

	public boolean isAdminSet() {
		return this.userRepository.existsByRoles_Name("ROLE_ADMIN");
	}

	public User createUser(String email, String password) {
		return this.userRepository.save(new User(email, this.passwordEncoder.encode(password)));
	}

	public User createAdmin(String email, String password) {
		if (this.userRepository.existsById(email)) return null;
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_ADMIN"));
		return this.userRepository.save(new User(email, this.passwordEncoder.encode(password),roles));
	}

	public User createTeacher(String email, String password) {
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_TEACHER"));
		return this.userRepository.save(new User(email, this.passwordEncoder.encode(password),roles));
	}

	public User getUserInstance(String email) {
		return this.userRepository.getReferenceById(email);
	}
}
