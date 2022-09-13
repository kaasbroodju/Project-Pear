package com.oep.hu.pear.forms.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record StudentOldFormDTO(UUID id, String name, String templateName, Instant finishedAt) {
}
