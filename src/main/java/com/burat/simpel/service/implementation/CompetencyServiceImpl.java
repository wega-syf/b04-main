package com.burat.simpel.service.implementation;

import com.burat.simpel.model.Competency;
import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.repository.CompetencyDb;
import com.burat.simpel.service.CompetencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompetencyServiceImpl implements CompetencyService {
    @Autowired
    CompetencyDb competencyDb;

    @Override
    public List<Competency> getListCompetency() {
        return competencyDb.findAll();
    }

    @Override
    public Competency getCompetencyById(Long idComp) {
        return competencyDb.findByIdComp(idComp);
    }

    @Override
    public void addCompetency(Competency competency) {
        competencyDb.save(competency);
    }

    @Override
    public void deleteCompetency(Competency competency) {
        competencyDb.delete(competency);
    }

    @Override
    public Competency processCompetencyData(Competency competency) {
        List<CompetencyLevel> listCompetencyLevel = competency.getCompetencyModelList();
        Integer level = 1;
        for (CompetencyLevel cl : listCompetencyLevel) {
            cl.setIdCompetency(competency);
            cl.setLevel(level);
            level++;
        }
        competency.setCompetencyModelList(listCompetencyLevel);

        return competency;
    }

    @Override
    public Competency prepareCompetencyTemplate() {
        Competency competency = new Competency();
        List<CompetencyLevel> listCompetencyLevel =  new ArrayList<>();
        for (int i=0; i<5; i++) {
            CompetencyLevel competencyLevel = new CompetencyLevel();
            listCompetencyLevel.add(competencyLevel);
        }
        competency.setCompetencyModelList(listCompetencyLevel);

        return competency;
    }
}
