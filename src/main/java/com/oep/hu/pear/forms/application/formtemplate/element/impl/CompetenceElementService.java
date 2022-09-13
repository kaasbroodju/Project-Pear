package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.assessmentmatrix.data.CompetenceRepository;
import com.oep.hu.pear.assessmentmatrix.domain.criteria.Progress;
import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.assessmentmatrix.domain.hboi.CompetenceId;
import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.data.CompetenceElementRepository;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.impl.competenceelement.CompetenceElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CompetenceElementService extends ElementService<CompetenceElement> {

	private CompetenceRepository repository;
	private CompetenceElementRepository competenceElementRepository;

	@Override
	public void setInnerValue(Map<String, Object> dto, CompetenceElement element) {
		if (dto.get(element.getName()) == null) return;
		String competenceString = (String) dto.get(element.getName());
		String[] strings = competenceString.split("\\(\\)", 3);

		Competence competence;
		try {
			competence = repository.getReferenceById(new CompetenceId(strings[0], strings[1], Short.parseShort(strings[2])));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("competence does not exists");
		}

		element.setCompetence(competence);
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, CompetenceElement element) {
		if (dto.get("progress" + element.getName() + "Reviewer") != null) {
			element.setProgress(Progress.valueOf((String) dto.get("progress" + element.getName() + "Reviewer")));
		}

		if (dto.get(element.getName() + "Reviewer") != null) {
			element.setExplanation((String) dto.get(element.getName() + "Reviewer"));
		}
	}

	@Override
	public void getInnerValue(Map<String, Object> values, CompetenceElement element) {
		Competence competence = element.getCompetence();
		if (competence == null) return;
		values.put(element.getName(), competence.getMainCompetence() + " " + competence.getSubCompetence() + " " + competence.getLevel());
		values.put(element.getName() + "Description", element.getCompetence().getDescription());
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, CompetenceElement element) {
		values.put("progress" + element.getName() + "Reviewer", element.getProgress());
		values.put(element.getName() + "Reviewer", element.getExplanation());

	}

	public Competence getCompetenceByForm(Form form) {
		return this.competenceElementRepository.findByForm(form).map(CompetenceElement::getCompetence).orElse(null);
	}

}
