package com.oep.hu.pear.users.data;

import com.oep.hu.pear.users.domain.Group;
import com.oep.hu.pear.users.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {
	boolean existsByCoach_EmailAndStudentsIsIn(String email, Collection<Student> students);

	boolean existsByCoach_EmailAndStudents(String email, Student students);

	List<Group> findByCoach_Email(String email);




}
