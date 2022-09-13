package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.TeacherService;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/teacher")
@AllArgsConstructor
public class AdminTeacherController {

	private TeacherService service;

	@Operation(summary = "Get list of all teachers names and corresponding email")
	@GetMapping
	public List<PersonDTO> getAllTeachers() {
		return this.service.getAllTeachers().stream().map(teacher -> new PersonDTO(teacher.getEmail(), teacher.getEmail())).toList();
	}
}
