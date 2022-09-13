package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/student")
@AllArgsConstructor
public class AdminStudentController {

	private StudentService service;

	@Operation(summary = "Give list of student's that match the given email")
	@GetMapping("/search/{email}")
	public List<PersonDTO> searchStudentsByEmail(@PathVariable String email) {
		return this.service.searchStudentsByEmail(email).stream().map(student -> new PersonDTO(student.getEmail(), student.getName())).toList();
	}
}
