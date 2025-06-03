package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainingCatController {
    @Autowired
    TrainingService trainingService;

    @Autowired
    CompetencyService competencyService;

    @GetMapping("/training-catalog")
    private String viewTrainingCatalog(Model model) {
        List<TrainingModel> listTraining = trainingService.getListTraining();
        model.addAttribute("listTraining", listTraining);
        return "training-catalog";
    }

    @GetMapping("/training-catalog/detail")
    private String detailTrainingCatalog(@RequestParam(value = "trainingId") Long trainingId, Model model) {
        TrainingModel training = trainingService.getTrainingById(trainingId);
        model.addAttribute("training", training);
        return "training-detail";
    }

    @GetMapping("/training-catalog/add")
    public String addTrainingFormPage(Model model) {
        TrainingModel training = new TrainingModel();
        model.addAttribute("training", training);

        List<CompetencyLevel> listCompLevel = new ArrayList<>();
        CompetencyLevel competencyLevel = new CompetencyLevel();
        listCompLevel.add(competencyLevel);
        training.setLevelOfTraining(listCompLevel);

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);

        return "form-add-training";
    }

    @PostMapping(value = "/training-catalog/add", params = {"addRow"})
    private String addRowCompetencyMultiple(@ModelAttribute TrainingModel training, Model model) {
        if (training.getLevelOfTraining() == null || training.getLevelOfTraining().size() == 0) {
            training.setLevelOfTraining(new ArrayList<>());
        }
        training.getLevelOfTraining().add(new CompetencyLevel());
        model.addAttribute("training", training);

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);

        return "form-add-training";
    }
    @PostMapping(value = "/training-catalog/add", params = {"deleteRow"})
    private String deleteRowCompetencyMultiple(
            @ModelAttribute TrainingModel training,
            @RequestParam("deleteRow") Integer row,
            Model model) {
        final Integer rowId = Integer.valueOf(row);
        training.getLevelOfTraining().remove(rowId.intValue());

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);
        model.addAttribute("training", training);

        return  "form-add-training";
    }
    @PostMapping(value = "/training-catalog/add", params = {"save"})
    public String addTrainingSubmit(@ModelAttribute TrainingModel training, RedirectAttributes redirectAttributes) {
        if (training.getLevelOfTraining() == null || training.getLevelOfTraining().size() == 0) {
            training.setLevelOfTraining(new ArrayList<>());
        }
        training.setLevelOfTraining(trainingService.processLevelOfTraining(training.getLevelOfTraining()));
        trainingService.addTraining(training);

        String successMessage = "Training bernama " + training.getNama() + " berhasil didaftarkan.";
        redirectAttributes.addFlashAttribute("successtext",successMessage);

        return "redirect:/training-catalog";
    }

    @GetMapping("/training-catalog/delete/{trainingId}")
    public String deleteTraining(@PathVariable(value = "trainingId") Long trainingId, Model model) {
        TrainingModel training = trainingService.getTrainingById(trainingId);
        trainingService.deleteTraining(training);

        model.addAttribute("listTraining", trainingService.getListTraining());

        return "training-catalog";
    }
    @GetMapping("/training-catalog/update/{trainingId}")
    public String updateTrainingFormPage(@PathVariable(value = "trainingId") Long trainingId, Model model) {
        TrainingModel training = trainingService.getTrainingById(trainingId);
        model.addAttribute("training", training);

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);

        return "form-update-training";
    }

    @PostMapping(value = "/training-catalog/update", params = {"save"})
    public String updateTrainingSubmit(@ModelAttribute TrainingModel training, RedirectAttributes redirectAttributes) {
        if (training.getLevelOfTraining() == null || training.getLevelOfTraining().size() == 0) {
            training.setLevelOfTraining(new ArrayList<>());
        }
        training.setLevelOfTraining(trainingService.processLevelOfTraining(training.getLevelOfTraining()));
        trainingService.addTraining(training);

        String successMessage = "Training bernama " + training.getNama() + " berhasil di-update.";
        redirectAttributes.addFlashAttribute("successtext",successMessage);
        return "redirect:/training-catalog";
    }

    @PostMapping(value = "/training-catalog/update", params = {"addRow"})
    public String addRowCompetencyMultipleForUpdate(@ModelAttribute TrainingModel training, Model model) {
        if (training.getLevelOfTraining() == null || training.getLevelOfTraining().size() == 0) {
            training.setLevelOfTraining(new ArrayList<>());
        }
        training.getLevelOfTraining().add(new CompetencyLevel());
        model.addAttribute("training", training);

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);
        return "form-update-training";
    }

    @PostMapping(value = "/training-catalog/update", params = {"deleteRow"})
    private String deleteRowCompetencyMultipleForUpdate(
            @ModelAttribute TrainingModel training,
            @RequestParam("deleteRow") Integer row,
            Model model) {
        final Integer rowId = Integer.valueOf(row);
        training.getLevelOfTraining().remove(rowId.intValue());

        List<Competency> listCompAvailable = competencyService.getListCompetency();
        model.addAttribute("listCompAvailable", listCompAvailable);
        model.addAttribute("training", training);

        return  "form-update-training";
    }
}
