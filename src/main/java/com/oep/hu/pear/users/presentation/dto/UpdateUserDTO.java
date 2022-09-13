package com.oep.hu.pear.users.presentation.dto;

import java.util.List;

public record UpdateUserDTO(String name, Boolean isApprentice, List<String> badges) {
}
