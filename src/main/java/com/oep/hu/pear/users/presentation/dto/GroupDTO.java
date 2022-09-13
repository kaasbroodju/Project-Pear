package com.oep.hu.pear.users.presentation.dto;

import java.util.List;

public record GroupDTO(PersonDTO coach, List<PersonDTO> students) {
}
