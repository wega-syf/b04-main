package com.burat.simpel.controller;

import com.burat.simpel.dto.TrainingRecDTO;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;
import com.burat.simpel.service.TrainingRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrainingRecController {
    @Autowired
    TrainingRecService trainingRecService;

    @GetMapping("/training-recommendation")
    private String viewTrainingRecommendationPeriode(Model model) {
        List<EventPeriodModel> listEventPeriod = trainingRecService.getAllEventPeriodByJenis("assessment");
        model.addAttribute("listEventPeriod", listEventPeriod);
        return "periode-rekomendasi";
    }

    @GetMapping("/training-recommendation/period")
    private String viewTrainingRecommendationMainPage(@RequestParam(value = "idEventPriod") Long idEventPriod, Model model) {
        List<TrainingRecDTO> listTrainingRecDTO = trainingRecService.getAllTrainingRecDTO(idEventPriod);
        model.addAttribute("listTrainingRec", listTrainingRecDTO);
        model.addAttribute("idEventPriod", idEventPriod);
        return "training-recommendation";
    }

    @GetMapping("/training-recommendation/detail")
    private String viewTrainingRecommendationDetailPage(@RequestParam(value = "idLevel") Long idLevel, @RequestParam(value = "idEventPriod") Long idEventPriod, Model model) {
        List<ReviewAssessmentModel> listDetail = trainingRecService.getAllReviewAssessmentByCompLevel(idLevel, idEventPriod);
        model.addAttribute("idEventPriod", idEventPriod);
        model.addAttribute("listDetail", listDetail);
        return "training-recommendation-detail";
    }
}
