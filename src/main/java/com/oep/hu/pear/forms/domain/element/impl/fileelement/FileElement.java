package com.oep.hu.pear.forms.domain.element.impl.fileelement;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
public class FileElement extends FormElement {

	@ElementCollection
	@CollectionTable(name = "file_element_entries",
			joinColumns = {
				@JoinColumn(name = "file_element_form_id", referencedColumnName = "form_id"),
				@JoinColumn(name = "file_element_name", referencedColumnName = "name")
	})
	@MapKeyColumn(name = "file_name")
	@Column(name = "file_location")
	private Map<String, String> files = new HashMap<>();

	public FileElement(String name, Form form) {
		super(name, form);
	}
}
