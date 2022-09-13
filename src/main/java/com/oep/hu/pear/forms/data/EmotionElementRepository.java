package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElementId;
import com.oep.hu.pear.forms.domain.element.impl.emotionelement.EmotionElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface EmotionElementRepository extends JpaRepository<EmotionElement, FormElementId> {
	List<EmotionElement> findByFormIn(Collection<Form> forms);


	List<EmotionElement> findByFormIsInAndForm_FinishedAtIsNotNull(Collection<Form> forms);
}
