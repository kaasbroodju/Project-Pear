package com.oep.hu.pear.forms.domain.template;

import com.oep.hu.pear.forms.domain.ReviewPolicy;
import com.oep.hu.pear.users.domain.Person;

import java.util.HashSet;
import java.util.Set;

public class FormTemplateBuilder {

	protected String templateName;
	protected ReviewPolicy reviewPolicy = null;
	private Set<Class<? extends Person>> canBeCreatedBy = new HashSet<>();
	private int maxAllowedDrafts = 1;


	public FormTemplateBuilder(String name) {
		this.templateName = name;
	}

	public FormTemplateBuilder setMaxAllowedDrafts(int maxAllowedDrafts) {
		this.maxAllowedDrafts = maxAllowedDrafts;
		return this;
	}

	public FormTemplateBuilder setReviewPolicy(ReviewPolicy reviewPolicy) {
		this.reviewPolicy = reviewPolicy;
		return this;
	}

	public FormTemplateBuilder addCreator(Class<? extends Person> personChildClass) {
		canBeCreatedBy.add(personChildClass);
		return this;
	}

	public FormTemplate build() {
		if (!(canBeCreatedBy.size() > 0)) return null;
		return new FormTemplate(templateName, reviewPolicy, canBeCreatedBy, maxAllowedDrafts);
	}
}
