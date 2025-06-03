package com.burat.simpel.service;

import com.burat.simpel.model.AssessmentModel;
import com.burat.simpel.model.AssessorModel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    void add(AssessmentModel assessmentModel);

    Boolean checkDuplicate(AssessmentModel assessmentModel);

    List<UserModel> getAssessedUser(AssessorModel assessorModel, EventPeriodModel eventPeriodModel);

    List<UserModel> getNotAssessedUser(AssessorModel assessorModel, EventPeriodModel eventPeriodModel);


    List<AssessmentModel> getAllAssessmentModel();

    List<AssessmentModel> getAssessmentModelByPeriod(Long eventPeriodModelId);

    List<AssessmentModel> getAssessmentModelByAssessor(String assessorUuid);

    List<AssessmentModel> getAssessmentModelByPeriodAndAssessor(Long eventPeriodModelId, String userUuid);

    Optional<AssessmentModel> getAssessmentModelById(Long id);


}
