package com.oep.hu.pear.users.presentation.dto;

import java.util.List;

public record GuildDTO(
        String name,
        List<PersonDTO> guildMasters,
        List<PersonDTO> apprentices,
        List<PersonDTO> students
) {}