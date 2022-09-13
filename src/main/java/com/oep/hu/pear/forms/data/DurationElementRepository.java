package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElementId;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DurationElementRepository extends JpaRepository<DurationElement, FormElementId> {
	List<DurationElement> findByFormIsInAndForm_FinishedAtIsNotNull(Collection<Form> forms);


}
