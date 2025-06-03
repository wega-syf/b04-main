package com.burat.simpel.service.implementation;

import com.burat.simpel.model.AssessmentModel;
import com.burat.simpel.model.AssessorModel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.AssessmentDb;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    AssessmentDb assessmentDb;
    @Autowired
    UserDb userDb;

    @Override
    public void add(AssessmentModel assessmentModel) {
        assessmentDb.save(assessmentModel);
    }

    @Override
    public Boolean checkDuplicate(AssessmentModel assessmentModel) {
        return assessmentDb.checkDuplicateByUserAssessorEvent(
                assessmentModel.getAssessor().getAccountUuid(),
                assessmentModel.getUser().getAccountUuid(),
                assessmentModel.getEvent().getIdEventPriod()
                ).isPresent();
    }

    @Override
    public List<AssessmentModel> getAllAssessmentModel() {
        return assessmentDb.findAll();
    }

    @Override
    public List<UserModel> getAssessedUser(AssessorModel assessorModel, EventPeriodModel eventPeriodModel) {
        return userDb.findUserAlreadyAssessedByAssessorInEvent(assessorModel.getAccountUuid(), eventPeriodModel.getIdEventPriod());
    }

    @Override
    public List<UserModel> getNotAssessedUser(AssessorModel assessorModel, EventPeriodModel eventPeriodModel) {
        return userDb.findUserNotAssessedByAssessorIn(assessorModel.getAccountUuid(), eventPeriodModel.getIdEventPriod());
    }

    @Override
    public List<AssessmentModel> getAssessmentModelByPeriod(Long eventPeriodModelId) {
        return assessmentDb.findAllByEventPeriodId(eventPeriodModelId);
    }

    @Override
    public List<AssessmentModel> getAssessmentModelByAssessor(String assessorUuid) {
        return assessmentDb.findAllByAssessor(assessorUuid);
    }

    @Override
    public List<AssessmentModel> getAssessmentModelByPeriodAndAssessor(Long eventPeriodModelId, String assessorUuid) {
        return assessmentDb.findAllByEventPeriodAndAssessor(eventPeriodModelId, assessorUuid);
    }

    @Override
    public Optional<AssessmentModel> getAssessmentModelById(Long id){
        return assessmentDb.findById(id);
    }
}
