package com.burat.simpel.repository;
import com.burat.simpel.model.DivisiModel;
import com.burat.simpel.model.TitleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleDb extends JpaRepository<TitleModel, Long> {

}
