package com.oep.hu.pear.assessmentmatrix.application;

import com.oep.hu.pear.assessmentmatrix.data.CompetenceRepository;
import com.oep.hu.pear.assessmentmatrix.domain.hboi.Competence;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetenceService {
    private CompetenceRepository repository;

    public void createCompetence(String mainCompetence, String subCompetence, short level, String description) {
        this.repository.save(new Competence(mainCompetence, subCompetence, level, description));
    }

    public List<Competence> getAll() {
        return this.repository.findAll();
    }
}
