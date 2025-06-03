package com.burat.simpel.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.burat.simpel.model.EventPeriodModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.repository.TrainingPlanDb;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.TrainingPlanService;

@Service
public class TrainingPlanServiceImpl implements TrainingPlanService {
    @Autowired
    TrainingPlanDb trainingPlanDb;

    @Autowired
    UserDb userDb;

    @Override
    public List<TrainingPlanModel> getAllTrainingPlan() {
        List<TrainingPlanModel> listTrainingPlan = trainingPlanDb.findAllSort();
        List<TrainingPlanModel> listTrainingPlan2 = trainingPlanDb.findAllSortDone();
        listTrainingPlan.addAll(listTrainingPlan2);

        return listTrainingPlan;
    }

    @Override
    public int getJumlahTrainingDone() {
        List<TrainingPlanModel> listTrainingPlan = trainingPlanDb.findAllSortDone();

        return listTrainingPlan.size();
    }

    @Override
    public int getJumlahTrainingActive() {
        List<TrainingPlanModel> listTrainingPlan  = trainingPlanDb.findAllSortActive();

        return listTrainingPlan.size();
    }

    @Override
    public int getJumlahTrainingConfirmed() {
        List<TrainingPlanModel> listTrainingPlan  = trainingPlanDb.findAllSortConfirmed();

        return listTrainingPlan.size();
    }

    @Override
    public TrainingPlanModel getTrainingPlanById(Long id) {
        Optional<TrainingPlanModel> trainingPlan = trainingPlanDb.findById(id);
        if (trainingPlan.isPresent()) {
            return trainingPlan.get();
        } else
            return null;
    }

    @Override
    public void addTrainingPlan(TrainingPlanModel trainingPlanModel) {
        trainingPlanDb.save(trainingPlanModel);
    }

    @Override
    public void deleteTrainingPlan(TrainingPlanModel trainingPlanModel) {
        trainingPlanDb.delete(trainingPlanModel);
    }


    @Override
    public boolean TrainingDateValidationCheckWithEvent(TrainingPlanModel trainingPlanModel, EventPeriodModel eventPeriodModel){
        boolean dateStartValid = trainingPlanModel.getDateStart().isAfter(eventPeriodModel.getDateStart()) && trainingPlanModel.getDateEnd().isBefore(eventPeriodModel.getDateEnd());
        boolean dateEndValid = trainingPlanModel.getDateStart().isAfter(eventPeriodModel.getDateStart()) && trainingPlanModel.getDateEnd().isBefore(eventPeriodModel.getDateEnd());
        return dateStartValid && dateEndValid;
    }
}
