package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.forms.application.OpenReviewService;
import com.oep.hu.pear.forms.application.exceptions.OpenReviewAlreadyExistsException;
import com.oep.hu.pear.forms.application.formtemplate.FormTemplateService;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.domain.*;
import com.oep.hu.pear.forms.task.exception.FormIsLockedException;
import com.oep.hu.pear.forms.task.exception.NoSelfReviewException;
import com.oep.hu.pear.forms.task.exception.ReviewerUnableToGiveDegreeOfAssessmentException;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OpenReviewTask {

	private OpenReviewService openReviewService;
	private PersonService personService;
	private RefactorFormService formService;
//	private ReviewableFormRepository formRepository;
	private FormTemplateService formTemplateService;
	private StudentService studentService;

	@Transactional
	public void addToBoard(String user, UUID id, String title, DegreeOfAssessment degreeOfAssessment) throws FormIsLockedException, EntityNotFoundException, OpenReviewAlreadyExistsException {
		Student student = this.studentService.getStudentByEmail(user);
		Form form = student.getCurrentBook().getDrafts().stream().filter((formInBook -> formInBook.getId().equals(id))).findFirst().orElseThrow(EntityNotFoundException::new);

		if (this.formTemplateService.getFormTemplate(form.getTemplateName()).getReviewPolicy() != ReviewPolicy.DEFAULT) throw new FormIsLockedException();
		if (form.getReviewer() != null) throw new FormIsLockedException();
		if (form.getFormStatus() != FormStatus.IN_LIMBO) throw new FormIsLockedException();
		if (title.isBlank() && title.length() < 5) throw new IllegalArgumentException("Title is too short");
		if (degreeOfAssessment == null) throw new IllegalArgumentException("Wanted degree of assessment is null");
		form.setFormStatus(FormStatus.RESERVED);
		this.formService.update(form);
		this.openReviewService.addOpenReviewToBoard(title, degreeOfAssessment, form, student);

//		this.formRepository.save(form);
	}

	@Transactional
	public void claimOpenReview(String userEmail, UUID id) throws NoSelfReviewException, ReviewerUnableToGiveDegreeOfAssessmentException {
		OpenReview openReview = this.openReviewService.getOpenReview(id);
		Person potentialReviewer = this.personService.getPerson(userEmail);
		if (potentialReviewer == openReview.getPerson()) throw new NoSelfReviewException();
		if (Arrays.stream(potentialReviewer.ableToGiveTypesOfReviews()).noneMatch(reviewType -> reviewType == openReview.getWantedDegreeOfAssessment())) throw new ReviewerUnableToGiveDegreeOfAssessmentException();
		openReview.getFormToBeReviewed().setReviewer(potentialReviewer);
		this.openReviewService.removeOpenReviewFromBoard(id);
	}

	public List<OpenReview> getAll(String name) {
		return this.openReviewService.getAll(this.personService.getPerson(name));
	}
}
