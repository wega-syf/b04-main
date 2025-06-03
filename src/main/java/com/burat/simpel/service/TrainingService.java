package com.burat.simpel.service;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;

import java.util.List;

public interface TrainingService {
    List<TrainingModel> getListTraining();
    TrainingModel getTrainingById(Long trainingID);
    void addTraining(TrainingModel training);
    List<CompetencyLevel> processLevelOfTraining(List<CompetencyLevel> levelOfTraining);
    void deleteTraining(TrainingModel training);
    public List<TrainingModel> getSomeTraining();
}
