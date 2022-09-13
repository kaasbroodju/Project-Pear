package com.oep.hu.pear.assessmentmatrix.domain.criteria;

import com.oep.hu.pear.forms.domain.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataPointId implements Serializable {

	private DataPointType type;
	private Form form;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DataPointId that = (DataPointId) o;
		return Objects.equals(type, that.type) && Objects.equals(form, that.form);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, form);
	}
}
