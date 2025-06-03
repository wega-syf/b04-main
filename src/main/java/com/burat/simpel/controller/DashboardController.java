package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {
    final String BASE_URL = "/dashboard";

    @Autowired
    TrainingTakenByUserService trainingTakenByUserService;

    @Autowired
    TrainingPlanService trainingPlanService;

    @Autowired
    ReviewTrainingService reviewTrainingService;

    @GetMapping("/dashboard")
    private String viewDashboard(Model model) {

        List<TrainingPlanTakenByUserModel> listTraining = trainingTakenByUserService.getTrainingTaken();
        model.addAttribute("listTraining", listTraining);
        return "dashboard";
    }

    @GetMapping("/dashboard/{idTrainingPlan}")
    private String viewDetailTrainingTaken(@PathVariable Long idTrainingPlan, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(idTrainingPlan);
        model.addAttribute("listUserTrainingPlan", trainingPlanModel.getUserInTrainingPlan());
        model.addAttribute("idTrainingPlan", trainingPlanModel.getIdTrainingPlan());
        model.addAttribute("idTraining", trainingPlanModel.getIdTraining());
        model.addAttribute("nama", trainingPlanModel.getNama());
        model.addAttribute("deskripsi", trainingPlanModel.getDeskripsi());
        model.addAttribute("dateStart", trainingPlanModel.getDateStart());
        model.addAttribute("dateEnd", trainingPlanModel.getDateEnd());
        model.addAttribute("budget", trainingPlanModel.getBudget());
        model.addAttribute("status", trainingPlanModel.getStatus());

        ReviewTrainingModel review = reviewTrainingService.find(trainingPlanModel);

        model.addAttribute("review", review);

        return "dashboard-training-plan";
    }
}
