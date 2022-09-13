package com.oep.hu.pear.forms.domain.element;

import com.oep.hu.pear.forms.domain.Form;

import java.io.Serializable;
import java.util.Objects;

public class FormElementId implements Serializable {

	private String name;
	private Form form;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FormElementId that = (FormElementId) o;
		return Objects.equals(name, that.name) && Objects.equals(form, that.form);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, form);
	}
}
