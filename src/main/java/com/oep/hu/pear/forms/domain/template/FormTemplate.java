package com.oep.hu.pear.forms.domain.template;

import com.oep.hu.pear.forms.domain.ReviewPolicy;
import com.oep.hu.pear.users.domain.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter @Setter
public class FormTemplate {

	@Id
	private String templateName;

	@OneToOne
	private FormTemplateLayout currentVersion;

	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<FormTemplateLayout> versions = new ArrayList<>();

	@ElementCollection
	private Set<Class<? extends Person>> canBeCreatedBy = new HashSet<>();

	private ReviewPolicy reviewPolicy;

	private int maxAllowedDrafts;

	public FormTemplate(String templateName, ReviewPolicy reviewPolicy, Set<Class<? extends Person>> canBeCreatedBy, int maxAllowedDrafts) {
		this.templateName = templateName;
		this.canBeCreatedBy = canBeCreatedBy;
		this.reviewPolicy = reviewPolicy;
		this.maxAllowedDrafts = maxAllowedDrafts;
	}

	public void addVersion(FormTemplateLayout newestVersion) {
		this.currentVersion = newestVersion;
		this.versions.add(newestVersion);
	}
}
