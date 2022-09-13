package com.oep.hu.pear.forms.domain.element;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oep.hu.pear.forms.task.exception.InvalidFormElementValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@MappedSuperclass
@Entity
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorColumn(length = 127)
public abstract class FormElementValidator {
	
	@Id
	@GeneratedValue
	@JsonIgnore
	private long id;

	public abstract void validateInnerValue(FormElement element) throws InvalidFormElementValue;

	public abstract void validateInnerValueReviewer(FormElement element) throws InvalidFormElementValue;

}
