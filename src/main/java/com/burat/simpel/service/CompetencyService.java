package com.burat.simpel.service;

import com.burat.simpel.model.Competency;

import java.util.List;

public interface CompetencyService {
    List<Competency> getListCompetency();
    Competency getCompetencyById(Long idComp);
    void addCompetency(Competency competency);
    void deleteCompetency(Competency competency);

    Competency processCompetencyData(Competency competency);

    Competency prepareCompetencyTemplate();
}
