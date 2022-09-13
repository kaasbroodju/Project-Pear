package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.domain.Badge;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.presentation.dto.PersonWithRoleDTO;
import com.oep.hu.pear.users.presentation.dto.UpdateUserDTO;
import com.oep.hu.pear.users.presentation.dto.UserDetailsDTO;
import com.oep.hu.pear.users.task.AdminUserTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@AllArgsConstructor
public class AdminUserController {

	private PersonService personService;
	private AdminUserTask task;

	@Operation(summary = "Get all users with tuple (email, name, role)")
	@GetMapping
	public List<PersonWithRoleDTO> users() {
		return this.personService.getAll().stream().map(person -> new PersonWithRoleDTO(person.getEmail(), person.getName(), person.getClass().getSimpleName())).toList();
	}

	@Operation(summary = "Get details of user")
	@GetMapping("/{email}")
	public UserDetailsDTO getUserProfile(@PathVariable String email) {
		Person person = this.personService.getPerson(email);
		return new UserDetailsDTO(person.getEmail(), person.getName(), Hibernate.getClass(person).getSimpleName(), person.getBadges().stream().map(Badge::getName).toList());
	}

	@Operation(summary = "Update details of user")
	@PatchMapping("/{email}")
	public void updateUserProfile(@RequestBody UpdateUserDTO dto, @PathVariable String email) {
		this.task.updateUser(email, dto.name(), dto.isApprentice(), dto.badges());
	}
}
