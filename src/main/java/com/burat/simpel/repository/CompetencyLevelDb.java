package com.burat.simpel.repository;

import com.burat.simpel.model.Competency;
import com.burat.simpel.model.CompetencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyLevelDb extends JpaRepository<CompetencyLevel, Long> {
    CompetencyLevel getCompetencyLevelByIdCompetencyAndLevel(Competency idCompetency, Integer level);

    @Query(nativeQuery =true,value = "select count(id_comp) from competency_level where id_comp= (:id_comp)")
    Integer countCompetencyLevelByCompetencyId(@Param("id_comp") Long id_comp);

}
