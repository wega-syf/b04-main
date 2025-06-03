package com.burat.simpel.service.implementation;

import com.burat.simpel.repository.CompetencyLevelDb;
import com.burat.simpel.service.CompetencyLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetencyLevelServiceImpl implements CompetencyLevelService {
    @Autowired
    CompetencyLevelDb competencyLevelDb;

    @Override
    public Integer countLevelOfCompetency(Long idCompetency) {
        return competencyLevelDb.countCompetencyLevelByCompetencyId(idCompetency);
    }
}
