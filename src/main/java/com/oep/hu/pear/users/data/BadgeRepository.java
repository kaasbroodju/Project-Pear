package com.oep.hu.pear.users.data;

import com.oep.hu.pear.users.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, String> {
}
