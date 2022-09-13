package com.oep.hu.pear.users.presentation.dto;

import java.util.List;

public record UserDetailsDTO(String email, String name, String role, List<String> badges) {
}
