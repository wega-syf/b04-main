package com.burat.simpel.service;

import java.util.List;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.CompetencyModel;

public interface CompetencyModelService {
    List<CompetencyModel> getAllCompetencyModel();
    CompetencyModel getCompetencyModelById(Long id);
    public void addCompetencyModel(CompetencyModel competencyModel);
    List<CompetencyLevel> extractCompetencyLevel(List<CompetencyLevel> listCompetencyLevel);
    void deleteCompetencyModel(CompetencyModel competencyModel);

}
