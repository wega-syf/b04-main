package com.burat.simpel.service.implementation;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.TrainingPlanTakenByUserModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.TrainingTakenByUserDb;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.TrainingTakenByUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TrainingTakenByUserServiceImpl implements TrainingTakenByUserService {
    @Autowired
    TrainingTakenByUserDb trainingTakenByUserDb;

    @Autowired
    UserDb userDb;

    @Override
    public void deleteTrainingTakenByUserService(TrainingPlanTakenByUserModel model) {
        trainingTakenByUserDb.delete(model);
    }

    public List<TrainingPlanTakenByUserModel> getTrainingTaken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserModel user = userDb.findByUsername(currentPrincipalName);

        List<TrainingPlanTakenByUserModel> listTraining = trainingTakenByUserDb.findTrainingPlanTakenByUserModelByStatus(user);
        List<TrainingPlanTakenByUserModel> listTraining2 = trainingTakenByUserDb.findTrainingPlanTakenByUserModelByStatusDone(user);
        listTraining.addAll(listTraining2);

        return listTraining;
    }

    public TrainingPlanTakenByUserModel getTrainingTakenById(Long idTrainingPlanTakenUser) {
        TrainingPlanTakenByUserModel model = trainingTakenByUserDb.findByIdTrainingPlanTakenUser(idTrainingPlanTakenUser);
        if (model != null) {
            return model;
        } else return new TrainingPlanTakenByUserModel();
    }

    public List<TrainingPlanTakenByUserModel> getTrainingTakenByTrainingPlan(TrainingPlanModel trainingPlanModel) {
        List<TrainingPlanTakenByUserModel> listTrainingTaken = trainingTakenByUserDb.findTrainingPlanTakenByUserModelByTrainingPlan(trainingPlanModel);
        return listTrainingTaken;
    }


}
