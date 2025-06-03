package com.burat.simpel.service;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.TrainingPlanTakenByUserModel;

import java.util.List;

public interface TrainingTakenByUserService {
    void deleteTrainingTakenByUserService(TrainingPlanTakenByUserModel model);
    List<TrainingPlanTakenByUserModel> getTrainingTaken();
    TrainingPlanTakenByUserModel getTrainingTakenById(Long idTrainingPlanTakenUser);
    List<TrainingPlanTakenByUserModel> getTrainingTakenByTrainingPlan(TrainingPlanModel trainingPlanModel);
}
