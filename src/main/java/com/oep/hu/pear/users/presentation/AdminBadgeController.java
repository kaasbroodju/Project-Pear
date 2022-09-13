package com.oep.hu.pear.users.presentation;

import com.oep.hu.pear.users.domain.Badge;
import com.oep.hu.pear.users.task.BadgeTask;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/badge")
@AllArgsConstructor
public class AdminBadgeController {

	private BadgeTask badgeTask;

	@Operation(summary = "Get list of restricted badges")
	@GetMapping
	public List<String> getBadgesGroupedByGroup() {
		return this.badgeTask.getBadgesGroupedByGroup().get(null).stream().map(Badge::getName).toList();
	}
}
