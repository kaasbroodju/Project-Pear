package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.presentation.dto.formOverviewDTO;
import com.oep.hu.pear.users.task.DraftsTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drafts")
@AllArgsConstructor
public class DraftsController {

    private DraftsTask task;

    @Operation(summary = "Get user's drafts")
    @GetMapping
    public List<formOverviewDTO> getEntries(Authentication authentication) {
        return this.task.myDrafts(authentication.getName()).stream().map(form -> new formOverviewDTO(form.getId(), form.getTemplateName(), form.getName(), form.getFinishedAt())).toList();
    }
}
