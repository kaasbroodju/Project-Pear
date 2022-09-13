package com.oep.hu.pear.assessmentmatrix.data;

import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import com.oep.hu.pear.assessmentmatrix.domain.hboi.CompetenceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, CompetenceId> {


}
