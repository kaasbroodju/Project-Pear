package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.domain.Guild;
import com.oep.hu.pear.users.presentation.dto.GuildDTO;
import com.oep.hu.pear.users.presentation.dto.PersonDTO;
import com.oep.hu.pear.users.task.GuildTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/guild")
@AllArgsConstructor
public class GuildController {

    private GuildTask task;

    private GuildDTO toDTO(Guild guild, String userEmail) {
        return new GuildDTO(
                guild.getName(),
                guild.getGuildMasters().stream().filter(guildMaster -> !guildMaster.getEmail().equals(userEmail)).map(gm -> new PersonDTO(gm.getEmail(), gm.getName())).toList(),
                guild.getApprentices().stream().filter(apprentice -> !apprentice.getEmail().equals(userEmail)).map(gm -> new PersonDTO(gm.getEmail(), gm.getName())).toList(),
                guild.getStudents().stream().filter(student -> !student.getEmail().equals(userEmail)).map(gm -> new PersonDTO(gm.getEmail(), gm.getName())).toList()
        );
    }

    @Operation(summary = "Get user's guild info in full detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
    })
    @GetMapping
    public GuildDTO getUserGuild(Authentication authentication) {
        Guild myGuild = this.task.getMyGuild(authentication.getName());
        if (myGuild == null) return null;
        return toDTO(myGuild, authentication.getName());
    }

    @Operation(summary = "Get user's guild name")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
    })
    @GetMapping("/name")
    public String getUserGuildName(Authentication authentication) {
        Guild myGuild = this.task.getMyGuild(authentication.getName());
        if (myGuild == null) return "";
        return myGuild.getName();
    }

    @Operation(summary = "Get guild's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404"),

    })
    @GetMapping("/{guildName}")
    public GuildDTO getGuild(Authentication authentication, @PathVariable String guildName) {
        try {
            return toDTO(this.task.getById(guildName), authentication.getName());
        } catch (EntityNotFoundException enfe) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get list of names of all guilds")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
    })
    @GetMapping("/name/all")
    public List<String> getAllNames() {
        return this.task.getGuildNames();
    }

}
