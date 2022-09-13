package com.oep.hu.pear.forms.domain.element;

import com.oep.hu.pear.forms.domain.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(FormElementId.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DiscriminatorColumn(length = 127)
public abstract class FormElement {

	@Id
	protected String name;

	@Id
	@ManyToOne
	protected Form form;
}
