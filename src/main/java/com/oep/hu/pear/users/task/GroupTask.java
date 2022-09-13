package com.oep.hu.pear.users.task;

import com.oep.hu.pear.users.application.GroupService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.application.TeacherService;
import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Student;
import com.oep.hu.pear.users.domain.Teacher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupTask {

	private GroupService groupService;
	private StudentService studentService;
	private TeacherService teacherService;

	@Transactional
	public void createGroup(String groupName, String coachEmail, List<String> studentEmails) {
		Teacher coach = this.teacherService.getTeacher(coachEmail);
		List<Student> students = this.studentService.getStudentsByEmail(studentEmails);
		if (students.size() != studentEmails.size()) return; // todo throw error
		this.groupService.createGroup(groupName, coach, students);
	}

	public List<Group> getAllGroups() {
		return this.groupService.getAllGroups();
	}

	// create group
	public void createGroup(String groupName) {
		if (groupName.isBlank()) throw new IllegalArgumentException("Name is blank");
		this.groupService.createGroup(groupName);
	}

	public Group getGroup(String name) {
		return this.groupService.getGroup(name);
	}

	@Transactional
	public void addStudentToGroup(String groupName, String studentEmail) {
		Group group = getGroup(groupName);
		Student student = this.studentService.getStudentByEmail(studentEmail);
		Group previousGroup = student.getGroup();
		if (previousGroup != null) previousGroup.removeStudent(student);
		group.addStudent(student);
	}

	@Transactional
	public void deleteStudentFromGroup(String groupName, String studentEmail) {
		Group group = getGroup(groupName);
		Student student = this.studentService.getStudentByEmail(studentEmail);
		Group previousGroup = student.getGroup();
		if (previousGroup != null) previousGroup.removeStudent(student);
		group.removeStudent(student);
	}

	public void changeCoach(String groupName, String coachEmail) {
		Group group = getGroup(groupName);
		Teacher teacher = this.teacherService.getTeacher(coachEmail);
		group.setCoach(teacher);
		this.groupService.saveGroup(group);
	}

	@Transactional
	public void deleteGroup(String groupName) {
		Group group = getGroup(groupName);
		group.setCoach(null);
		List.copyOf(group.getStudents()).forEach(group::removeStudent);
		this.groupService.deleteGroup(groupName);
	}
}
