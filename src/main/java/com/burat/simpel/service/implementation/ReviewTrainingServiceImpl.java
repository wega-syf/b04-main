package com.burat.simpel.service.implementation;

import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.UserDb;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.burat.simpel.model.ReviewTrainingModel;
import com.burat.simpel.repository.ReviewTrainingDb;
import com.burat.simpel.service.ReviewTrainingService;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.None;

@Service
public class ReviewTrainingServiceImpl implements ReviewTrainingService {
    @Autowired
    private ReviewTrainingDb reviewTrainingDb;

    @Autowired
    private UserDb userDb;

    @Override
    public Float getAverageRating(ReviewTrainingModel reviewTrainingModel) {
        int[] rate = { reviewTrainingModel.getQ1(), reviewTrainingModel.getQ2(), reviewTrainingModel.getQ3(),
                reviewTrainingModel.getQ4(), reviewTrainingModel.getQ5(), reviewTrainingModel.getQ6(),
                reviewTrainingModel.getQ7(), reviewTrainingModel.getQ8(), reviewTrainingModel.getQ9(),
                reviewTrainingModel.getQ10() };

        int sum = 0;
        for (int number : rate) {
            sum += number;
        }
        float average = (float) sum / rate.length;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedAverage = decimalFormat.format(average);
        float rating = Float.parseFloat(roundedAverage);
        return rating;
    }

    @Override
    public Boolean checkNull(ReviewTrainingModel reviewTrainingModel) {
        Boolean q1 = reviewTrainingModel.getQ1() == null;
        Boolean q2 = reviewTrainingModel.getQ2() == null;
        Boolean q3 = reviewTrainingModel.getQ3() == null;
        Boolean q4 = reviewTrainingModel.getQ4() == null;
        Boolean q5 = reviewTrainingModel.getQ5() == null;
        Boolean q6 = reviewTrainingModel.getQ6() == null;
        Boolean q7 = reviewTrainingModel.getQ7() == null;
        Boolean q8 = reviewTrainingModel.getQ8() == null;
        Boolean q9 = reviewTrainingModel.getQ9() == null;
        Boolean q10 = reviewTrainingModel.getQ10() == null;

        Boolean flag = (q1 || q2 || q3 || q4 || q5 || q6 || q7 || q8 || q9 || q10);
        return flag;
    }

    @Override
    public void addReviewTraining(ReviewTrainingModel reviewTrainingModel) {
        reviewTrainingDb.save(reviewTrainingModel);
    }

    @Override
    public ReviewTrainingModel find(TrainingPlanModel trainingPlanModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserModel user = userDb.findByUsername(currentPrincipalName);
        return reviewTrainingDb.findReviewTrainingModelByIdTrainPlanAndUser(trainingPlanModel, user);
    }

    @Override
    public List<ReviewTrainingModel> getReviewInTrainingPlan(TrainingModel trainingModel) {
        return reviewTrainingDb.findReviewInTrainingModel(trainingModel);
    }

    @Override
    public Float getAverageReviewInTrainingPlan(TrainingModel trainingModel) {
        float tmp = 0;
        int counter = 0;
        float flag = 0;
        for (ReviewTrainingModel training : reviewTrainingDb.findReviewInTrainingModel(trainingModel)) {
            if (!training.getRatingAverage().isNaN()) {
                tmp += training.getRatingAverage();
                counter += 1;
            }
        }
        float average = counter == 0 ? 0 : (float) tmp / counter;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String roundedAverage = decimalFormat.format(average);
        float avgRating = Float.parseFloat(roundedAverage);
        return avgRating;
    }
}