package com.burat.simpel.service;

import java.util.List;

import com.burat.simpel.model.ReviewTrainingModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;

public interface ReviewTrainingService {
    Float getAverageRating(ReviewTrainingModel reviewTrainingModel);
    Boolean checkNull(ReviewTrainingModel reviewTrainingModel);
    void addReviewTraining(ReviewTrainingModel reviewTrainingModel);
    ReviewTrainingModel find(TrainingPlanModel trainingPlanModel);
    List<ReviewTrainingModel> getReviewInTrainingPlan(TrainingModel trainingModel);
    Float getAverageReviewInTrainingPlan(TrainingModel trainingModel);
}
