package com.burat.simpel.service.implementation;

import com.burat.simpel.model.AdminModel;
import com.burat.simpel.model.AssessorModel;
import com.burat.simpel.repository.AssessorDb;
import com.burat.simpel.service.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessorServiceImpl implements AssessorService {
    @Autowired
    private AssessorDb assessorDb;

    @Override
    public List<AssessorModel> getAllAssessor() {
        return assessorDb.findAll();
    }

    @Override
    public AssessorModel addAssessor(AssessorModel assessor) {
        assessorDb.save(assessor);
        return assessor;
    }


    @Override
    public void deleteAccountByUuid(String uuid) {
        assessorDb.deleteById(uuid);
    }

    @Override
    public Optional<AssessorModel> getByUuid(String uuid) {
        return assessorDb.findById(uuid);
    }

    @Override
    public AssessorModel getByUsername(String username) {
        return assessorDb.findByUsername(username);
    }
    @Override
    public List<String> getUsername() {
        return assessorDb.findUsernameAssessor();
    }
}
