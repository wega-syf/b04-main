package com.burat.simpel.service.implementation;

import com.burat.simpel.model.ExecutiveModel;
import com.burat.simpel.repository.ExecutiveDb;
import com.burat.simpel.service.ExecutiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExecutiveServiceImpl implements ExecutiveService {
    @Autowired
    private ExecutiveDb executiveDb;

    @Override
    public List<ExecutiveModel> getAllExecutive() {
        return executiveDb.findAll();
    }

    @Override
    public ExecutiveModel addExecutive(ExecutiveModel executive) {
        executiveDb.save(executive);
        return executive;
    }


    @Override
    public void deleteAccountByUuid(String uuid) {
        executiveDb.deleteById(uuid);
    }

    @Override
    public Optional<ExecutiveModel> getByUuid(String uuid) {
        return executiveDb.findById(uuid);
    }

    @Override
    public ExecutiveModel getByUsername(String username) {
        return executiveDb.findByUsername(username);
    }

    @Override
    public List<String> getUsername() {
        return executiveDb.findUsernameExecutive();
    }
}
