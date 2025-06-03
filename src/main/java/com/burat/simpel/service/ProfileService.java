package com.burat.simpel.service;

import com.burat.simpel.model.*;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    AdminModel getAdmin();
    UserModel getUser();
    AssessorModel getAssessor();
    ExecutiveModel getExecutive();
}
