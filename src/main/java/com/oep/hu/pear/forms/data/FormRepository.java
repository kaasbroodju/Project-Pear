package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.users.domain.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

//@Repository
public interface FormRepository extends JpaRepository<Form, UUID> {
	List<Form> findByReviewerAndFinishedAtIsNull(Person reviewer);

	List<Form> findByReviewerAndFinishedAtIsNotNull(Person reviewer, Pageable pageable);

	List<Form> findByReviewer_EmailAndFinishedAtIsNotNullAndFinishedAtIsAfter(String email, Instant finishedAt);





	long countByReviewer(Person reviewer);

	long countByReviewerAndFinishedAtIsNull(Person reviewer);







}
