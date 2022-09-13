package com.oep.hu.pear.forms.presentation;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.presentation.dto.StudentOldFormDTO;
import com.oep.hu.pear.users.application.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teacher/student/form")
@AllArgsConstructor
public class TeacherFormController {

	private StudentService service;

	@Operation(summary = "Get completed forms of student of their current book")
	@GetMapping("/{email}/old")
	public List<StudentOldFormDTO> getTemplates(Authentication authentication, @PathVariable String email) {
		return this.service.getStudentByEmail(email).getCurrentBook().getDones()
				.stream()
				.sorted(Comparator.comparing(Form::getFinishedAt).reversed())
				.map(form -> new StudentOldFormDTO(form.getId(), form.getName(), form.getTemplateName(), form.getFinishedAt()))
				.toList();
	}
}
