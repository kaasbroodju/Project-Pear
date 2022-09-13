package com.oep.hu.pear.users.presentation.dto;

import com.oep.hu.pear.forms.domain.element.impl.emotionelement.Emotion;

import java.time.Instant;

public record EmotionDTO(Instant timestamp, Emotion emotion) {
}
