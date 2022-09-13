package com.oep.hu.pear.forms.application.formtemplate;

import com.oep.hu.pear.forms.data.FormRepository;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.users.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefactorFormService {

	private FormRepository repository;
	private static final byte PAGE_SIZE = 10;

	public Form createForm(Form form) {
		return this.repository.save(form);
	}

	public Form getForm(UUID id) {
		return this.repository.getReferenceById(id);
	}

	public Form update(Form form) {
		return this.repository.save(form);
	}

	public void delete(Form form) {
		this.repository.delete(form);
	}

	public void addReviewer(Form form, Person reviewer, DegreeOfAssessment degreeOfAssessment) {
		form.setReviewer(reviewer);
		form.setFormStatus(FormStatus.UNDER_REVIEW);
		form.setDegreeOfAssessment(degreeOfAssessment);
		this.repository.save(form);
	}

	public void changeName(Form form, String name) {
		form.setName(name);
		this.repository.save(form);
	}

	public List<Form> getPendingReviewsAssignedToReviewer(Person reviewer) {
		return this.repository.findByReviewerAndFinishedAtIsNull(reviewer);
	}

	public long getPendingAmountForReviewer(Person reviewer) {
		return this.repository.countByReviewerAndFinishedAtIsNull(reviewer);
	}

	public List<Form> getOldReviewsAssignedToReviewer(Person reviewer, int page) {
		return this.repository.findByReviewerAndFinishedAtIsNotNull(reviewer, PageRequest.of(page, PAGE_SIZE));
	}


	public List<Form> getOldReviewsAssignedToReviewer(String reviewerEmail, Instant afterInstant) {
		return this.repository.findByReviewer_EmailAndFinishedAtIsNotNullAndFinishedAtIsAfter(reviewerEmail, afterInstant);
	}
}
