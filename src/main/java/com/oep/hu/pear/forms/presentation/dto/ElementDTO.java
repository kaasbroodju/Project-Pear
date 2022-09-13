package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.forms.domain.element.FormElementValidator;

public record ElementDTO(String name, String label, String type, FormElementValidator validator) {
}
