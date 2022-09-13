package com.oep.hu.pear.users.presentation.dto;

import java.time.Instant;

public record formOverviewDTO(java.util.UUID id, String type, String name, Instant finishedAt) {
}
