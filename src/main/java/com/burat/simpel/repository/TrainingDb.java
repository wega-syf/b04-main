package com.burat.simpel.repository;

import com.burat.simpel.model.TrainingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDb extends JpaRepository<TrainingModel, Long> {
    @Query(nativeQuery =true, value = "select * from training a where a.id_training not in (SELECT DISTINCT b.id_training FROM training_plan b);")
    List<TrainingModel> findTrainingNotExisting();
    
    TrainingModel findByTrainingId(Long trainingId);
}
