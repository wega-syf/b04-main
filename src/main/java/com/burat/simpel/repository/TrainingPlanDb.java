package com.burat.simpel.repository;

import java.util.List;

import com.burat.simpel.model.TrainingPlanTakenByUserModel;
import com.burat.simpel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrainingPlanDb extends JpaRepository<TrainingPlanModel, Long>{

    @Query("SELECT a FROM TrainingPlanModel a WHERE a.status != 3 ORDER BY a.status DESC, a.dateStart ASC")
    List<TrainingPlanModel> findAllSort();

    @Query("SELECT a FROM TrainingPlanModel a WHERE a.status = 3 ORDER BY a.dateStart DESC")
    List<TrainingPlanModel> findAllSortDone();

    @Query("SELECT a FROM TrainingPlanModel a WHERE a.status = 2 ORDER BY a.dateStart DESC")
    List<TrainingPlanModel> findAllSortActive();
    
    @Query("SELECT a FROM TrainingPlanModel a WHERE a.status = 1 ORDER BY a.dateStart DESC")
    List<TrainingPlanModel> findAllSortConfirmed();
}
