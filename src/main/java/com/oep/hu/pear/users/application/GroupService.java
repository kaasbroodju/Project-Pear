package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.GroupRepository;
import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Student;
import com.oep.hu.pear.users.domain.Teacher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupService {

	private GroupRepository repository;

	@Transactional
	public void createGroup(String groupName, Teacher coach, List<Student> students) {
		this.repository.save(new Group(groupName, students, coach));
	}

	public void createGroup(String groupName) {
		this.repository.save(new Group(groupName));
	}

	public List<Group> getAllGroups() {
		return this.repository.findAll();
	}

	public Group getGroup(String groupName) {
		return this.repository.getReferenceById(groupName);
	}

	public Group saveGroup(Group group) {
		return this.repository.save(group);
	}

	public void deleteGroup(String groupName) {
		this.repository.deleteById(groupName);
	}

	public List<Group> getGroupsByTeacher(String email) {
		return this.repository.findByCoach_Email(email);
	}
}
