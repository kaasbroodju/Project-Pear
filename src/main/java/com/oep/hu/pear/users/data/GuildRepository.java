package com.oep.hu.pear.users.data;

import com.oep.hu.pear.users.domain.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<Guild, String> {
}
