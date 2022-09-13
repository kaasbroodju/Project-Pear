package com.oep.hu.pear.security.data;

import com.oep.hu.pear.security.domain.RegisterAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface RegisterAttemptRepository extends JpaRepository<RegisterAttempt, UUID> {
	boolean existsByMailAndCreatedAtIsBefore(String mail, Instant createdAt);
}
