package com.oep.hu.pear.users.presentation.dto;

import java.time.Instant;

public record ProgressDurationDTO(Instant timestamp, long duration) {
}
