package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.application.GroupService;
import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Teacher;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/teacher/group")
@AllArgsConstructor
public class TeacherGroupController {

	private GroupService groupService;

	@Operation(summary = "Get list of user's (teacher) student groups")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
	})
	@GetMapping
	public List<String> getMyGroups(Authentication authentication) {
		return this.groupService.getGroupsByTeacher(authentication.getName()).stream()
				.map(Group::getGroupName).toList();
	}

	@Operation(summary = "Get list of student of group")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "group not found")
	})
	@GetMapping("/{groupName}/student")
	public List<PersonDTO> getStudentOfGroup(Authentication authentication, @PathVariable String groupName) {
		Group group;
		try {
			group = this.groupService.getGroup(groupName);
		} catch (EntityNotFoundException enfe) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Teacher coach = group.getCoach();
		if (coach == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		if (!coach.getEmail().equals(authentication.getName())) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return group.getStudents().stream().map(student -> new PersonDTO(student.getEmail(), student.getName())).toList();
	}
}
