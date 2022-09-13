package com.oep.hu.pear.users.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "student_group")
public class Group {

	@Id
	private String groupName;

	@OneToMany
	private List<Student> students = new ArrayList<>();

	@ManyToOne

	private Teacher coach;

	public Group(String groupName, List<Student> students, Teacher coach) {
		this.groupName = groupName;
		this.coach = coach;
		students.forEach(this::addStudent);
	}

	public Group(String groupName) {
		this.groupName = groupName;
	}

	public void addStudent(Student student) {
		students.add(student);
		student.setGroup(this);
	}

	public void removeStudent(Student student) {
		if (students.remove(student)) student.setGroup(null);
	}
}
