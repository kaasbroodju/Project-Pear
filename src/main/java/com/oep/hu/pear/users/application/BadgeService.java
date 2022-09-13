package com.oep.hu.pear.users.application;

import com.oep.hu.pear.users.data.BadgeRepository;
import com.oep.hu.pear.users.domain.Badge;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BadgeService {

	private BadgeRepository repository;

	public Badge createBadge(String name, String groupName) {
		return this.repository.save(new Badge(name, groupName));
	}

	public Badge getBadge(String badgeName) {
		return this.repository.getReferenceById(badgeName);
	}

	public Map<String, List<Badge>> getGroups() {
		Map<String, List<Badge>> output = new HashMap<>();
		for (Badge badge : this.repository.findAll()) {
			String group = badge.getBadgeGroup();
			if (output.containsKey(group)) {
				output.get(group).add(badge);
			} else {
				ArrayList<Badge> initialBadgeList = new ArrayList<>();
				initialBadgeList.add(badge);
				output.put(group, initialBadgeList);
			}
		}
		return output;

	}
}
