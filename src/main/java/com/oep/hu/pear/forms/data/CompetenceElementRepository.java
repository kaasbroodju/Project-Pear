package com.oep.hu.pear.forms.data;

import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.FormElementId;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetenceElementRepository extends JpaRepository<CompetenceElement, FormElementId> {
	Optional<CompetenceElement> findByForm(Form form);



}
