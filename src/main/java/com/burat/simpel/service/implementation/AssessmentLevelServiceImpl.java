package com.burat.simpel.service.implementation;

import com.burat.simpel.model.AssessmentLevelModel;
import com.burat.simpel.repository.AssessmentLevelDb;
import com.burat.simpel.service.AssessmentLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentLevelServiceImpl implements AssessmentLevelService {
    @Autowired
    AssessmentLevelDb assessmentlevelDb;

    @Override
    public void add(AssessmentLevelModel assessmentLevelModel) {
        assessmentlevelDb.save(assessmentLevelModel);
    }
}
