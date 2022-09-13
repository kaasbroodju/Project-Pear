package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.common.Triplet;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.application.formtemplate.element.impl.CompetenceElementService;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.forms.task.exception.FormIsLockedException;
import com.oep.hu.pear.forms.task.exception.NoSelfReviewException;
import com.oep.hu.pear.forms.task.exception.ReviewerUnableToGiveDegreeOfAssessmentException;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class ReviewTask {

	private RefactorFormService service;
	private PersonService personService;
	private StudentService studentService;
	private CompetenceElementService competenceElementService;

	public void addReviewer(String user, UUID id, String reviewersEmail, DegreeOfAssessment degreeOfAssessment) throws NoSelfReviewException, FormIsLockedException, ReviewerUnableToGiveDegreeOfAssessmentException {
		if (user.equals(reviewersEmail)) throw new NoSelfReviewException();
		Form form = this.service.getForm(id);
		if (!(form.getFormStatus() == FormStatus.IN_LIMBO || form.getFormStatus() == FormStatus.RESERVED)) throw new FormIsLockedException();
		if (form.getReviewer() != null) throw new FormIsLockedException();
		if (!Arrays.asList(personService.getPerson(reviewersEmail).ableToGiveTypesOfReviews()).contains(degreeOfAssessment)) throw new ReviewerUnableToGiveDegreeOfAssessmentException();

		this.service.addReviewer(form, this.personService.getPerson(reviewersEmail), degreeOfAssessment);
	}

	public DegreeOfAssessment[] getReviewTypesOfPerson(String email) {
		return this.personService.getPerson(email).ableToGiveTypesOfReviews();
	}

	public List<Triplet<Form, Person, Competence>> getPendingReviews(String email) {
		return translateToTriplet(this.service.getPendingReviewsAssignedToReviewer(this.personService.getPerson(email)));
	}

	public List<Triplet<Form, Person, Competence>> getAwaitingReviews(String email) {
		return translateToTriplet(this.studentService.getStudentByEmail(email).getCurrentBook().getUnderReview().stream().toList());
	}

	public List<Triplet<Form, Person, Competence>> getOldReviews(String email, int page) {
		return translateToTriplet(this.service.getOldReviewsAssignedToReviewer(this.personService.getPerson(email), page));
	}

	private List<Triplet<Form, Person, Competence>> translateToTriplet(List<Form> forms) {
		return forms.stream()
				.map(form -> {
					Student student = this.studentService.getStudentOfForm(form);
					Competence competence = this.competenceElementService.getCompetenceByForm(form);
					return new Triplet<Form, Person, Competence>(form, student, competence);
				}).toList();
	}

	public long getPendingReviewsAmount(String email) {
		return this.service.getPendingAmountForReviewer(this.personService.getPerson(email));
	}

	public void rejectReview(String reviewerEmail, UUID id) throws FormIsLockedException {
		Form form = this.service.getForm(id);
		Person reviewer = this.personService.getPerson(reviewerEmail);

		if (!form.getReviewer().equals(reviewer)) throw new EntityNotFoundException();
		if (form.getFormStatus() != FormStatus.UNDER_REVIEW) throw new FormIsLockedException();

		form.setReviewer(null);
		form.setFormStatus(FormStatus.CONCEPT);
		form.setDegreeOfAssessment(null);

		this.service.update(form);
	}

	public Set<Person> searchPersonByNameOrBadge(String name) {
		return this.personService.getStudentsWithNameOrBadgeName(name);
	}

	public List<Triplet<Form, Person, Competence>> getOldReviewsAfter(String email, Instant afterInstant) {
		return translateToTriplet(this.service.getOldReviewsAssignedToReviewer(email, afterInstant));
	}
}
