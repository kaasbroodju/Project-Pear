package com.oep.hu.pear.forms.application.formtemplate;

import com.oep.hu.pear.forms.data.FormTemplateRepository;
import com.oep.hu.pear.forms.data.FormTemplateVersionRepository;
import com.oep.hu.pear.forms.domain.template.FormTemplate;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayout;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayoutId;
import com.oep.hu.pear.users.domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FormTemplateService {

	private FormTemplateRepository repository;
	private FormTemplateVersionRepository templateVersionRepository;

	public FormTemplate create(FormTemplate build) {
		if (this.repository.existsById(build.getTemplateName())) return this.repository.getReferenceById(build.getTemplateName());
		return this.repository.save(build);
	}

	public FormTemplate getFormTemplate(String name) {
		return this.repository.getReferenceById(name);
	}

	@Transactional
	public void addLayoutVersionToTemplate(String templateName, FormTemplateLayout build) {
		FormTemplate formTemplate = this.repository.getReferenceById(templateName);
		FormTemplateLayout formTemplateLayout = this.templateVersionRepository.save(
				build
		);
		formTemplate.addVersion(formTemplateLayout);
		this.repository.save(formTemplate);
	}

	public List<String> getAllowedTemplates(Class<? extends Person> personClass) {
		return this.repository.findByCanBeCreatedBy(personClass).stream().map(FormTemplate::getTemplateName).toList();
	}

	public FormTemplateLayout getFormLayout(String templateName, long version) {
		return this.templateVersionRepository.getReferenceById(new FormTemplateLayoutId(templateName, version));
	}

	public List<String> getAllTemplateNames() {
		return this.repository.findAll().stream().map(FormTemplate::getTemplateName).toList();
	}
}
