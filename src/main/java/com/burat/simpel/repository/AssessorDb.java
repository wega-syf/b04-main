package com.burat.simpel.repository;

import com.burat.simpel.model.AssessorModel;
import com.burat.simpel.model.TitleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssessorDb extends JpaRepository<AssessorModel, String> {
    AssessorModel findByUsername(String username);

    @Query("SELECT DISTINCT a.username FROM AssessorModel a")
    List<String> findUsernameAssessor();
}
