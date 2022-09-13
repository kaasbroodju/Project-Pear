package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.forms.domain.ReviewPolicy;

import java.util.Map;

public record FormTemplateDTO(String templateName, Map<String, ElementDTO> elements, ReviewPolicy reviewPolicy) {
}
