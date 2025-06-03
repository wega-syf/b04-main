package com.burat.simpel.service.implementation;

import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.ProfileService;

import com.burat.simpel.model.*;
import com.burat.simpel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserDb userDb;

    @Autowired
    private AdminDb adminDb;

    @Autowired
    private AssessorDb assessorDb;

    @Autowired
    private ExecutiveDb executiveDb;

    public AdminModel getAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        AdminModel admin = adminDb.findByUsername(currentPrincipalName);

        if (admin != null){
            return admin;
        }
        else{
            return null;
        }

    }

    public UserModel getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserModel user = userDb.findByUsername(currentPrincipalName);

        if (user != null){
            return user;
        }
        else{
            return null;
        }

    }

    public AssessorModel getAssessor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        AssessorModel assessor = assessorDb.findByUsername(currentPrincipalName);

        if (assessor != null){
            return assessor;
        }
        else{
            return null;
        }
    }

    public ExecutiveModel getExecutive(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        ExecutiveModel executive = executiveDb.findByUsername(currentPrincipalName);

        if (executive != null){
            return executive;
        }
        else{
            return null;
        }
    }
}
