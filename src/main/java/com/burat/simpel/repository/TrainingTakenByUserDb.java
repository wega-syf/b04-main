package com.burat.simpel.repository;

import com.burat.simpel.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingTakenByUserDb extends JpaRepository<TrainingPlanTakenByUserModel,Long> {
    @Query("SELECT a FROM TrainingPlanTakenByUserModel a WHERE a.userModel = :user and a.trainingPlan.status > 0 and a.trainingPlan.status != 3 ORDER BY a.trainingPlan.status DESC, a.trainingPlan.dateStart ASC")
    List<TrainingPlanTakenByUserModel> findTrainingPlanTakenByUserModelByStatus(@Param("user") UserModel user);

    @Query("SELECT a FROM TrainingPlanTakenByUserModel a WHERE a.userModel = :user and a.trainingPlan.status = 3 ORDER BY a.trainingPlan.dateStart DESC")
    List<TrainingPlanTakenByUserModel> findTrainingPlanTakenByUserModelByStatusDone(@Param("user") UserModel user);

    TrainingPlanTakenByUserModel findByIdTrainingPlanTakenUser(Long idTrainingPlanTakenUser);

    @Query("SELECT a FROM TrainingPlanTakenByUserModel a WHERE a.trainingPlan = :trainingPlan ")
    List<TrainingPlanTakenByUserModel> findTrainingPlanTakenByUserModelByTrainingPlan(@Param("trainingPlan") TrainingPlanModel trainingPlan);
}
