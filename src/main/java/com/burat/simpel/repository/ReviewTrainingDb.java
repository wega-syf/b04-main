package com.burat.simpel.repository;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import com.burat.simpel.model.ReviewTrainingModel;
import com.burat.simpel.model.TrainingModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewTrainingDb extends JpaRepository<ReviewTrainingModel, Long> {
    @Query("SELECT a FROM ReviewTrainingModel a WHERE a.idTrainPlan = :trainingPlan and a.user =:user")
    ReviewTrainingModel findReviewTrainingModelByIdTrainPlanAndUser(@Param("trainingPlan") TrainingPlanModel trainingPlan,
                                                                    @Param("user") UserModel user);

    @Query("SELECT a FROM ReviewTrainingModel a WHERE a.idTrainPlan.idTraining = :trainingModel")
    List<ReviewTrainingModel> findReviewInTrainingModel(@Param("trainingModel") TrainingModel trainingModel);

}
