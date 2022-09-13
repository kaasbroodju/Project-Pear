package com.oep.hu.pear.forms.task;

import com.oep.hu.pear.assessmentmatrix.application.DataPointTypeService;
import com.oep.hu.pear.forms.application.FormAccessService;
import com.oep.hu.pear.forms.application.formtemplate.FormTemplateService;
import com.oep.hu.pear.forms.application.formtemplate.RefactorFormService;
import com.oep.hu.pear.forms.application.formtemplate.element.FormElementService;
import com.oep.hu.pear.forms.domain.DegreeOfAssessment;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.forms.domain.ReviewPolicy;
import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.element.impl.datapointelement.DataPointElement;
import com.oep.hu.pear.forms.domain.element.impl.sectionelement.SectionElement;
import com.oep.hu.pear.forms.domain.template.FormTemplate;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayout;
import com.oep.hu.pear.forms.domain.template.TemplateElement;
import com.oep.hu.pear.forms.task.exception.ElementNotSupportedException;
import com.oep.hu.pear.forms.task.exception.InvalidFormAccessException;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;
import com.oep.hu.pear.users.application.PersonService;
import com.oep.hu.pear.users.application.StudentService;
import com.oep.hu.pear.users.domain.Person;
import com.oep.hu.pear.users.domain.Student;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class FormTask {

	private RefactorFormService service;
	private FormTemplateService formTemplateService;
	private StudentService studentService;
	private PersonService personService;
	private DataPointTypeService dataPointTypeService;
	private FormElementService formElementService;
	private FormAccessService formAccessService;

	public UUID createForm(String user, String formTemplateName) {
		FormTemplate template = this.formTemplateService.getFormTemplate(formTemplateName);
		Person person = personService.getPerson(user);
		if (!template.getCanBeCreatedBy().contains(Hibernate.getClass(person))) throw new AccessDeniedException("User role not allowed to create" + formTemplateName);

		Form form = new Form();
		if (Hibernate.unproxy(person) instanceof Student student) {
			List<Form> draftsWithTemplate = student.getCurrentBook().getDrafts().stream().filter(formInBook -> formInBook.getTemplateName().equals(formTemplateName)).toList();
			if (draftsWithTemplate.size() >= template.getMaxAllowedDrafts()) {
				return draftsWithTemplate.stream().min(Comparator.comparing(Form::getCreatedAt)).get().getId();
			}
			form.setName(formTemplateName + String.format(" %03d", student.getCurrentBook().getForms().stream().filter(alreadyCreatedForm -> alreadyCreatedForm.getTemplateName().equals(formTemplateName)).count() + 1));
		} else {
			form.setName(formTemplateName);
		}

		FormTemplateLayout currentVersion = template.getCurrentVersion();
		form.setTemplateName(currentVersion.getTemplateName());
		form.setTemplateVersion(currentVersion.getVersion());
		form.setReviewPolicy(template.getReviewPolicy());

		List<FormElement> elements = form.getElements();
		for (TemplateElement templateElement : currentVersion.getTemplateElements()) {
			try {
				if (templateElement.getType().equals(SectionElement.class)) continue;
				FormElement formElement = templateElement.getType().getDeclaredConstructor(String.class, Form.class).newInstance(templateElement.getName(), form);

				if (formElement instanceof DataPointElement dataPointElement && this.dataPointTypeService.doesDataPointTypeExists(templateElement.getLabel())) dataPointElement.setDataPointType(dataPointTypeService.getDataPointType(templateElement.getLabel()));

				elements.add(formElement);
			} catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		form.setElements(elements);

		form = this.service.createForm(form);
		studentService.addFormToStudent(user, form);
		return form.getId();
	}

	private Form getForm(UUID formId) {
		return this.service.getForm(formId);
	}

	public Form getForm(String user, UUID formId) throws InvalidFormAccessException {
		if (!this.formAccessService.hasReadAccess(user, formId)) throw new InvalidFormAccessException();
		return this.service.getForm(formId);
	}

	public Map<String, Object> getFormValues(String user, UUID formId) throws InvalidFormAccessException {
		if (!this.formAccessService.hasReadAccess(user, formId)) throw new InvalidFormAccessException();
		Form form = this.getForm(formId);
		Map<String, Object> values = new LinkedHashMap<>();
		for (FormElement element : form.getElements()) {
			try {
				this.formElementService.getInnerValue(values, element);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	public void updateForm(String  user, UUID formId, Map<String, Object> dto) throws InvalidFormAccessException {
		if (!this.formAccessService.hasWriteAccess(user, formId)) throw new InvalidFormAccessException();
		Form form = this.getForm(formId);

			for (FormElement element : form.getElements()) {
				try {
					this.formElementService.setInnerValue(dto, element);
				} catch (ElementNotSupportedException e) {
					e.printStackTrace();
				}
			}

		this.service.update(form);
	}

	public void finishForm(String user, UUID formId, Map<String, Object> dto) throws InvalidFormElementValue, InvalidFormAccessException {
		updateForm(user, formId, dto);
		Form form = this.getForm(formId);
		FormTemplateLayout formLayout = this.formTemplateService.getFormLayout(form.getTemplateName(), form.getTemplateVersion());
		for (TemplateElement templateElement : formLayout.getTemplateElements()) {
			if (templateElement.getValidator() == null) continue;
			templateElement.getValidator().validateInnerValue(form.getFormElement(templateElement.getName()));
		}


		ReviewPolicy reviewPolicy = this.formTemplateService.getFormTemplate(form.getTemplateName()).getReviewPolicy();
		if (reviewPolicy != null) {
			this.handleReviewPolicy(reviewPolicy, user, form, dto);
		} else {
			form.setFormStatus(FormStatus.DONE);
			this.service.update(form);
		}
	}

	public void handleReviewPolicy(ReviewPolicy reviewPolicy, String user, Form originalForm, Map<String, Object> dto) throws InvalidFormAccessException {
		switch (reviewPolicy) {
			case AUTOMATIC_TO_GROUP -> {
				Student student = studentService.getStudentOfForm(originalForm);
				for (Student groupMember : student.getGroup().getStudents()) {
					if (groupMember.getEmail().equals(user)) continue;
					originalForm.setFormStatus(FormStatus.RESERVED);
					UUID newFormId = this.createForm(user, originalForm.getTemplateName());
					this.updateForm(user, newFormId, dto);
					Form newForm = this.service.getForm(newFormId);
					this.service.addReviewer(newForm, groupMember, DegreeOfAssessment.PEER);
					studentService.addFormToStudent(user, newForm);
				}
				break;
			}

			default -> {
				originalForm.setFormStatus(FormStatus.IN_LIMBO);
				this.service.update(originalForm);
				break;
			}
		}
	}
	
	public void updateFormReviewer(String user, UUID formId, Map<String, Object> dto) throws InvalidFormAccessException {
		if (!this.formAccessService.hasReviewWriteAccess(user, formId)) throw new InvalidFormAccessException();

		Form form = this.getForm(formId);
		try {
			for (FormElement element : form.getElements()) {
				this.formElementService.setInnerValueReviewer(dto, element);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.service.update(form);
	}

	public Map<String, Object> getFormValuesReviewer(String user, UUID formId) throws InvalidFormAccessException {
		if (!this.formAccessService.hasReviewReadAccess(user, formId)) throw new InvalidFormAccessException();

		Form form = this.getForm(formId);
		Map<String, Object> values = new LinkedHashMap<>();
		for (FormElement element : form.getElements()) {
			try {
				this.formElementService.getInnerValueReviewer(values, element);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	public void finishFormReviewer(String user, UUID formId, Map<String, Object> dto) throws InvalidFormElementValue, InvalidFormAccessException {
		updateFormReviewer(user, formId, dto);
		Form form = this.getForm(formId);
		FormTemplateLayout formLayout = this.formTemplateService.getFormLayout(form.getTemplateName(), form.getTemplateVersion());

		for (TemplateElement templateElement : formLayout.getTemplateElements()) {
			if (templateElement.getValidator() == null) continue;
			templateElement.getValidator().validateInnerValueReviewer(form.getFormElement(templateElement.getName()));
		}

		form.setFormStatus(FormStatus.DONE);


		this.service.update(form);
	}

	public void changeName(String user, UUID formId, String name) throws InvalidFormAccessException {
		if (!this.formAccessService.hasWriteAccess(user, formId)) throw new InvalidFormAccessException();
		this.service.changeName(this.getForm(formId), name);
	}
}
