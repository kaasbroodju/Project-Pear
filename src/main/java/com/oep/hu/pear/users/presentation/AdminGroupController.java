package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Teacher;
import com.oep.hu.pear.users.presentation.dto.GroupDTO;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import com.oep.hu.pear.users.task.GroupTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/group")
@AllArgsConstructor
public class AdminGroupController {

	private GroupTask task;

	@Operation(summary = "Get all names of groups")
	@GetMapping
	public List<String> getAllGroups() {
		return this.task.getAllGroups().stream().map(Group::getGroupName).toList();
	}

	@Operation(summary = "Create new group of given name")
	@PostMapping("/{name}")
	public void createGroup(@PathVariable String name) {
		this.task.createGroup(name);
	}

	@Operation(summary = "Get details of group")
	@GetMapping("/{name}")
	public GroupDTO getGroups(@PathVariable String name) {
		Group group = this.task.getGroup(name);
		Teacher coach = group.getCoach();
		return new GroupDTO(coach != null ? new PersonDTO(coach.getEmail(), coach.getName()) : null, group.getStudents().stream().map(student -> new PersonDTO(student.getEmail(), student.getName())).toList());
	}

	@Operation(summary = "Delete group and decouple students to that group")
	@DeleteMapping("/{name}")
	public void deleteGroup(@PathVariable String name) {
		try {
			this.task.deleteGroup(name);
		} catch (Exception e) {
			log.error("Failed to delete group", e);
		}
	}

	@Operation(summary = "Couple student to group")
	@PostMapping("/{groupName}/student/{studentEmail}")
	public void addStudentToGroup(@PathVariable String groupName, @PathVariable String studentEmail) {
		this.task.addStudentToGroup(groupName, studentEmail);
	}

	@Operation(summary = "Decouple student to group")
	@DeleteMapping("/{groupName}/student/{studentEmail}")
	public void deleteStudentFromGroup(@PathVariable String groupName, @PathVariable String studentEmail) {
		this.task.deleteStudentFromGroup(groupName, studentEmail);
	}

	@Operation(summary = "Change coach assigned to group")
	@PatchMapping("/{groupName}/coach/{coachEmail}")
	public void changeCoach(@PathVariable String groupName, @PathVariable String coachEmail) {
		this.task.changeCoach(groupName, coachEmail);
	}
}
