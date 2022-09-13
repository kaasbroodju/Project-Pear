package com.oep.hu.pear.forms.domain;

import com.oep.hu.pear.forms.domain.element.FormElement;
import com.oep.hu.pear.forms.domain.template.FormTemplateLayoutId;
import com.oep.hu.pear.users.domain.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Form {

	@Id
	@GeneratedValue
	protected UUID id;
	protected String name;
	protected FormStatus formStatus = FormStatus.CONCEPT;
	protected Instant createdAt = Instant.now();
	protected Instant finishedAt;

	protected String templateName;
	protected long templateVersion;
	protected ReviewPolicy reviewPolicy;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	protected List<FormElement> elements = new ArrayList<>();

	@ManyToOne
	protected Person reviewer;

	protected DegreeOfAssessment degreeOfAssessment;

	public void setFormStatus(FormStatus formStatus) {
		this.formStatus = formStatus;
		if (formStatus == FormStatus.DONE) finishedAt = Instant.now();
	}

	public FormElement getFormElement(String key) {
		return this.elements.stream().filter(e -> Objects.equals(e.getName(), key)).findFirst().orElse(null);
	}

	public FormTemplateLayoutId getFormTemplateLayoutId() {
		return new FormTemplateLayoutId(this.templateName, this.templateVersion);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Form)) return false;
		Form form = (Form) o;
		return id == form.id;
	}
}
