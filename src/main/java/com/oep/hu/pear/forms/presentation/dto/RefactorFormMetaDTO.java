package com.oep.hu.pear.forms.presentation.dto;

import com.oep.hu.pear.forms.domain.FormStatus;
import com.oep.hu.pear.forms.domain.ReviewPolicy;

import java.time.Instant;

public record RefactorFormMetaDTO(java.util.UUID id, String name, FormStatus formStatus, Instant createdAt, Instant finishedAt,
								  ReviewPolicy reviewPolicy, FormTemplateVersionIdDTO formTemplate) {
}
