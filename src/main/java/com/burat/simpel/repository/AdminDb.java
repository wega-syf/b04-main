package com.burat.simpel.repository;

import com.burat.simpel.model.AccountModel;
import com.burat.simpel.model.AdminModel;
import com.burat.simpel.model.TitleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminDb extends JpaRepository<AdminModel, String> {

    AdminModel findByUsername(String username);

    @Query("SELECT DISTINCT a.username FROM AdminModel a")
    List<String> findUsernameAdmin();

}
