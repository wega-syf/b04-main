package com.burat.simpel.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.burat.simpel.service.CompetencyModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.CompetencyModel;
import com.burat.simpel.repository.CompetencyLevelDb;
import com.burat.simpel.repository.CompetencyModelDb;

@Service
public class CompetencyModelServiceImpl implements CompetencyModelService {
    @Autowired
    CompetencyModelDb competencyModelDb;

    @Autowired
    CompetencyLevelDb competencyLevelDb;

    @Override
    public List<CompetencyModel> getAllCompetencyModel() {
        return competencyModelDb.findAll();
    }

    @Override
    public CompetencyModel getCompetencyModelById(Long id) {
        Optional<CompetencyModel> competencyModel = competencyModelDb.findById(id);
        if (competencyModel.isPresent()) {
            return competencyModel.get();
        } else
            return null;
    }

    @Override
    public void addCompetencyModel(CompetencyModel competencyModel) {
        competencyModelDb.save(competencyModel);
    }

    @Override
    public List<CompetencyLevel> extractCompetencyLevel(List<CompetencyLevel> listCompetencyLevel) {
        List<CompetencyLevel> extractedCompetencyLevel = new ArrayList<>();
        for (CompetencyLevel item : listCompetencyLevel) {
            CompetencyLevel competencyLevel = competencyLevelDb.getCompetencyLevelByIdCompetencyAndLevel(item.getIdCompetency(), item.getLevel());
            extractedCompetencyLevel.add(competencyLevel);
        }
        return extractedCompetencyLevel;
    }

    @Override
    public void deleteCompetencyModel(CompetencyModel competencyModel) {
        competencyModelDb.delete(competencyModel);
    }
   
}