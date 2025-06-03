package com.burat.simpel.repository;

import com.burat.simpel.model.DivisiModel;
import com.burat.simpel.model.ExecutiveModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisiDb extends JpaRepository<DivisiModel, Long> {

}
