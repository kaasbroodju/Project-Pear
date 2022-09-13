package com.oep.hu.pear.forms.application.formtemplate.element.impl;

import com.oep.hu.pear.forms.application.formtemplate.element.ElementService;
import com.oep.hu.pear.forms.data.DurationElementRepository;
import com.oep.hu.pear.forms.domain.Form;
import com.oep.hu.pear.forms.domain.element.impl.durationelement.DurationElement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class DurationElementService extends ElementService<DurationElement> {

	private DurationElementRepository repository;

	public List<DurationElement> getDurationPoints(Set<Form> forms) {
		return this.repository.findByFormIsInAndForm_FinishedAtIsNotNull(forms);
	}

	@Override
	public void setInnerValue(Map<String, Object> dto, DurationElement element) {
		if (dto.get(element.getName()) == null) return;
		// todo throw IllegalArgumentException for all setInnerValue
		if (!Pattern.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]", (String) dto.get(element.getName()))) throw new IllegalArgumentException("invalid time format");
		String[] timeString = ((String) dto.get(element.getName())).split(":", 2);
		element.setDuration(Duration.ZERO.plusHours(Long.parseLong(timeString[0])).plusMinutes(Long.parseLong(timeString[1])));
	}

	@Override
	public void setInnerValueReviewer(Map<String, Object> dto, DurationElement element) {

	}

	@Override
	public void getInnerValue(Map<String, Object> values, DurationElement element) {
		values.put(element.getName(), String.format("%02d:%02d", element.getDuration().toHoursPart(), element.getDuration().toMinutesPart()));
	}

	@Override
	public void getInnerValueReviewer(Map<String, Object> values, DurationElement element) {

	}

}
