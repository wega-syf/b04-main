package com.burat.simpel.repository;

import com.burat.simpel.model.AssessmentLevelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessmentLevelDb extends JpaRepository<AssessmentLevelModel,Long> {

}
