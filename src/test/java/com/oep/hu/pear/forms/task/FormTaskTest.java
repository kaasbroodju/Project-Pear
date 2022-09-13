package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.assessmentmatrix.application.DataPointTypeService;
import com.oep.hu.pear.forms.application.FormAccessService;
import com.oep.hu.pear.forms.application.formtemplate.FormTemplateService;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.application.formtemplate.element.FormElementService;
import com.oep.hu.pear.forms.task.exception.InvalidFormAccessException;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormTaskTest {

	@Test
	void createForm() {
		FormAccessService formAccessService = mock(FormAccessService.class);
		doReturn(true).when(formAccessService).hasWriteAccess(anyString(), any());
		FormTask formTask = new FormTask(mock(RefactorFormService.class), mock(FormTemplateService.class), mock(StudentService.class), mock(PersonService.class), mock(DataPointTypeService.class), mock(FormElementService.class), formAccessService);


	}

	@Test
	void getForm() {
	}

	@Test
	void getFormValues() {
	}

	@Test
	void updateForm() {
	}

	@Test
	void finishForm() {
	}

	@Test
	void handleReviewPolicy() {
	}

	@Test
	void updateFormReviewer() {
	}

	@Test
	void getFormValuesReviewer() {
	}

	@Test
	void finishFormReviewer() {
	}

	@Test
	void changeName() {
		FormAccessService formAccessService = mock(FormAccessService.class);
		doReturn(true).when(formAccessService).hasWriteAccess(anyString(), any());
		FormTask formTask = new FormTask(mock(RefactorFormService.class), mock(FormTemplateService.class), mock(StudentService.class), mock(PersonService.class), mock(DataPointTypeService.class), mock(FormElementService.class), formAccessService);

		assertDoesNotThrow(() -> formTask.changeName("", UUID.randomUUID(), "new name"));
	}

	@Test
	void changeNameInvalidAccess() {
		FormAccessService formAccessService = mock(FormAccessService.class);
		doReturn(false).when(formAccessService).hasWriteAccess(anyString(), any());
		FormTask formTask = new FormTask(mock(RefactorFormService.class), mock(FormTemplateService.class), mock(StudentService.class), mock(PersonService.class), mock(DataPointTypeService.class), mock(FormElementService.class), formAccessService);

		assertThrows(InvalidFormAccessException.class, () -> formTask.changeName("", UUID.randomUUID(), "new name"));
	}
}