package com.burat.simpel.service;

import com.burat.simpel.model.AdminModel;
import com.burat.simpel.model.AssessorModel;


import java.util.List;
import java.util.Optional;

public interface AssessorService {

    public List<AssessorModel> getAllAssessor();
    public AssessorModel addAssessor(AssessorModel assessor);

    void deleteAccountByUuid(String uuid);

    Optional<AssessorModel> getByUuid(String uuid);
    public List<String> getUsername();


    AssessorModel getByUsername(String username);
}
