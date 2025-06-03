package com.burat.simpel.repository;

import com.burat.simpel.model.ExecutiveModel;
import com.burat.simpel.model.TitleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExecutiveDb extends JpaRepository<ExecutiveModel, String> {
    ExecutiveModel findByUsername(String username);
    @Query("SELECT DISTINCT a.username FROM ExecutiveModel a")
    List<String> findUsernameExecutive();
}
