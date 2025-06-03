package com.burat.simpel.repository;

import com.burat.simpel.model.CompetencyModel;
import com.burat.simpel.model.TitleModel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface CompetencyModelDb extends JpaRepository<CompetencyModel, Long> {
    @Query("SELECT DISTINCT a.title FROM CompetencyModel a")
    List<TitleModel> findTitleExisting();
}