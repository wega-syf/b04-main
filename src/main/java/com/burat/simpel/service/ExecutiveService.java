package com.burat.simpel.service;

import com.burat.simpel.model.AssessorModel;
import com.burat.simpel.model.ExecutiveModel;


import java.util.List;
import java.util.Optional;

public interface ExecutiveService {

    public List<ExecutiveModel> getAllExecutive();
    public ExecutiveModel addExecutive(ExecutiveModel executive);

    void deleteAccountByUuid(String uuid);
    Optional<ExecutiveModel> getByUuid(String uuid);

    ExecutiveModel getByUsername(String username);

    public List<String> getUsername();


}
