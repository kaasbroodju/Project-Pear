package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.forms.domain.FormStatus;

public record FormMetaDataDTO(long id, String formInstance, FormStatus formStatus) {
}
