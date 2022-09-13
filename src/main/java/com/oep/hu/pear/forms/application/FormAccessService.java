package com.oep.hu.pear.forms.application;

import com.oep.hu.pear.forms.data.FormRepository;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.users.data.BookRepository;
import com.oep.hu.pear.users.data.GroupRepository;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FormAccessService {

	private BookRepository bookRepository;
	private GroupRepository groupRepository;
	private FormRepository formRepository;

	private enum AccessRole {
		STUDENT,
		REVIEWER,
		COACH
	}

	private AccessRole determineRelationTypeOfForm(String email, Form form) {

		Person reviewer = form.getReviewer();
		if (reviewer != null && reviewer.getEmail().equals(email)) return AccessRole.REVIEWER;

		Student student = bookRepository.findByForms(form).getStudent();
		if (student.getEmail().equals(email)) return AccessRole.STUDENT;

		if (groupRepository.existsByCoach_EmailAndStudents(email, student)) return AccessRole.COACH;

		return null;
	}

	public boolean hasReadAccess(String email, UUID formId) {
		return this.determineRelationTypeOfForm(email, this.formRepository.getReferenceById(formId)) != null;
	}

	public boolean hasWriteAccess(String email, UUID formId) {
		Form form = this.formRepository.getReferenceById(formId);
		return (form.getFormStatus() == FormStatus.CONCEPT || form.getFormStatus() == FormStatus.IN_LIMBO) && this.determineRelationTypeOfForm(email, form) == AccessRole.STUDENT;
	}

	public boolean hasReviewReadAccess(String email, UUID formId) {
		Form form = this.formRepository.getReferenceById(formId);

		return switch (this.determineRelationTypeOfForm(email, form)) {
			case STUDENT, COACH -> form.getFormStatus() == FormStatus.DONE;
			case REVIEWER -> true;
			case null -> false;
		};
	}

	public boolean hasReviewWriteAccess(String email, UUID formId) {
		Form form = this.formRepository.getReferenceById(formId);
		return (form.getFormStatus() == FormStatus.UNDER_REVIEW) && this.determineRelationTypeOfForm(email, form) == AccessRole.REVIEWER;
	}


}
