package com.burat.simpel.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.burat.simpel.model.ReviewTrainingModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.ReviewTrainingService;
import com.burat.simpel.service.TrainingPlanService;
import com.burat.simpel.service.TrainingService;

@Controller
public class ReviewTrainingController {
    @Autowired
    ReviewTrainingService reviewTrainingService;

    @Autowired
    TrainingPlanService trainingPlanService;

    @Autowired
    TrainingService trainingService;

    @Autowired
    UserDb userDb;

    @GetMapping(value = "/review-training/{idTrainPlan}")
    public String reviewTrainingForm(Model model, RedirectAttributes redirectAttributes,
            @PathVariable Long idTrainPlan) {
        ReviewTrainingModel reviewTrainingModel = new ReviewTrainingModel();
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(idTrainPlan);

        model.addAttribute("reviewTrainingModel", reviewTrainingModel);
        model.addAttribute("trainingPlanModel", trainingPlanModel);
        return "form-review-training";
    }

    @PostMapping(value = "/review-training/{idTrainPlan}", params = { "save" })
    public String reviewTrainingFormSubmit(@ModelAttribute ReviewTrainingModel reviewTrainingModel, Model model,
            RedirectAttributes redirectAttributes, @PathVariable Long idTrainPlan) {

        if (reviewTrainingService.checkNull(reviewTrainingModel)) {
            redirectAttributes.addFlashAttribute("errortext", "Level tidak boleh kosong!");
            return "redirect:" + "/" + "review-training" + "/" + idTrainPlan;
        }
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(idTrainPlan);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserModel user = userDb.findByUsername(currentPrincipalName);

        reviewTrainingModel.setDateWritten(LocalDate.now());
        reviewTrainingModel.setUser(user);
        reviewTrainingModel.setIdTrainPlan(trainingPlanModel);
        reviewTrainingModel.setRatingAverage(reviewTrainingService.getAverageRating(reviewTrainingModel));
        reviewTrainingService.addReviewTraining(reviewTrainingModel);
        redirectAttributes.addFlashAttribute("successtext", "Selamat! Review berhasil ditambahkan");
        return "redirect:/dashboard/" + idTrainPlan;
    }

    @GetMapping("/view-review/{idTraining}")
    public String viewReviewTraining(@ModelAttribute ReviewTrainingModel reviewTrainingModel, @PathVariable Long idTraining, Model model) {
        TrainingModel training = trainingService.getTrainingById(idTraining);
        List<ReviewTrainingModel> reviewTrainingModel2 = reviewTrainingService.getReviewInTrainingPlan(training);
        model.addAttribute("averageRating", reviewTrainingService.getAverageReviewInTrainingPlan(training));
        model.addAttribute("reviewTrainingModel", reviewTrainingModel);
        model.addAttribute("listReviewTraining", reviewTrainingModel2);
        model.addAttribute("trainingPlanModel", training);
        return "view-review-training";
    }

}
