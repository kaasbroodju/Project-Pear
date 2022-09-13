package com.oep.hu.pear.forms.domain.template;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
public class FormTemplateLayoutId implements Serializable {

	private String templateName;
	private Long version;

	public FormTemplateLayoutId(String templateName, Long version) {
		this.templateName = templateName;
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FormTemplateLayoutId)) return false;
		FormTemplateLayoutId that = (FormTemplateLayoutId) o;
		return Objects.equals(templateName, that.templateName) && Objects.equals(version, that.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(templateName, version);
	}

	@Override
	public String toString() {
		return "FormTemplateVersionId{" +
				"template=" + templateName +
				", version=" + version +
				'}';
	}
}
