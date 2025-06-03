package com.burat.simpel.repository;


import com.burat.simpel.model.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyDb extends JpaRepository<Competency, Long>{
    Competency findByIdComp(Long idComp);
}
