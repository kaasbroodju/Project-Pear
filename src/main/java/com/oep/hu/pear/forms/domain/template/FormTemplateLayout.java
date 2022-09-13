package com.oep.hu.pear.forms.domain.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@IdClass(FormTemplateLayoutId.class)
@NoArgsConstructor
@Getter
@Setter
public class FormTemplateLayout {

	@Id
	private String templateName;

	@Id
	@GeneratedValue
	private long version;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "position")
	private List<TemplateElement> templateElements = new ArrayList<>();

	public FormTemplateLayout(String templateName, List<TemplateElement> templateElements) {
		this.templateName = templateName;

		for (int i = 0; i < templateElements.size(); i++) {
			TemplateElement element = templateElements.get(i);
			element.setPosition(i);
			element.setTemplateVersion(this);
			this.templateElements.add(element);
		}
	}
}
