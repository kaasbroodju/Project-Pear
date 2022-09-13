package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher/student")
@AllArgsConstructor
public class TeacherStudentController {

	private StudentService studentService;

	@Operation(summary = "Get student's name")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404"),
	})
	@GetMapping("/{email}") // todo add /name to path uri
	public String getUserProfile(@PathVariable String email) {
		return this.studentService.getStudentByEmail(email).getName();
	}

}
