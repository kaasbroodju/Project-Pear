package com.oep.hu.pear.users.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.users.domain.Student;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends PersonRepository<Student> {



	@Query("select b.forms as form from Student s, Book b, Form f where f.formStatus = ?1 and s.email = ?2")
	List<Form> findDrafts(FormStatus formStatus, String email);

	Student findByBooks_Forms(Form forms);

	long deleteByBooks_Forms(Form forms);

	long deleteByBooks_Forms_Id(long id);

	@Query("select s from Student s where upper(s.email) like upper(?1) order by s.email")
	List<Student> findByEmailIsLikeIgnoreCaseOrderByEmailAsc(String email);
}
