package com.oep.hu.pear.forms.domain.template;

import java.util.ArrayList;
import java.util.List;

public class FormTemplateLayoutBuilder {

	protected String templateName;
	protected List<TemplateElement> templateElements = new ArrayList<>();

	public FormTemplateLayoutBuilder(String name) {
		this.templateName = name;
	}

	public FormTemplateLayoutBuilder addElement(TemplateElement templateElement) {
		templateElements.add(templateElement);
		return this;
	}

	public FormTemplateLayout build() {
		return new FormTemplateLayout(templateName, templateElements);
	}
}
