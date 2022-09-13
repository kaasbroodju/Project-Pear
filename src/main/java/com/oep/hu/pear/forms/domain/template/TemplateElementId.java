package com.oep.hu.pear.forms.domain.template;

import java.io.Serializable;
import java.util.Objects;

public class TemplateElementId implements Serializable {

	private FormTemplateLayout templateVersion;
	private String name;
	private int position;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TemplateElementId templateElementId = (TemplateElementId) o;
		return position == templateElementId.position && Objects.equals(templateVersion, templateElementId.templateVersion) && Objects.equals(name, templateElementId.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(templateVersion, name, position);
	}


}
