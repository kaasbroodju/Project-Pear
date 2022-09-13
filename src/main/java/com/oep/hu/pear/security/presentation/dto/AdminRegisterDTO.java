package com.oep.hu.pear.security.presentation.dto;

import com.oep.hu.pear.security.domain.RegisterRole;

public record AdminRegisterDTO(String email, RegisterRole role) {
}
