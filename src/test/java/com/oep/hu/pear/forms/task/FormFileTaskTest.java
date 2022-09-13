package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.forms.application.FileUploadService;
import com.oep.hu.pear.forms.application.exceptions.InvalidFormElementTypeException;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.application.formtemplate.element.impl.FileElementService;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.impl.sectionelement.SectionElement;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FormFileTaskTest {

	private RefactorFormService refactorFormService;
	private FileUploadService fileUploadService;
	private FileElementService fileElementService;

	@BeforeEach
	void setup() {

	}

	@AfterEach
	void deleteFiles() {

	}

	@Test
	void saveFileToFormFormNotFoundTest() {
		RefactorFormService formService = mock(RefactorFormService.class);
		doThrow(EntityNotFoundException.class).when(formService).getForm(isA(UUID.class));
		FormFileTask formFileTask = new FormFileTask(formService, mock(FileUploadService.class), mock(FileElementService.class));

		assertThrows(EntityNotFoundException.class, () -> formFileTask.saveFileToForm(UUID.randomUUID(), "", mock(MultipartFile.class)));
	}

	@Test
	void saveFileToFormFormInvalidFormElementTest() {
		RefactorFormService formService = mock(RefactorFormService.class);
		Form form = mock(Form.class);
		doReturn(new SectionElement()).when(form).getFormElement(anyString());
		doReturn(form).when(formService).getForm(isA(UUID.class));
		FormFileTask formFileTask = new FormFileTask(formService, mock(FileUploadService.class), mock(FileElementService.class));

		assertThrows(InvalidFormElementTypeException.class, () -> formFileTask.saveFileToForm(UUID.randomUUID(), "", mock(MultipartFile.class)));
	}

}