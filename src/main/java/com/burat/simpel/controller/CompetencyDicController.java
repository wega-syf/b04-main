package com.burat.simpel.controller;

import com.burat.simpel.model.Competency;
import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.service.CompetencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CompetencyDicController {
    @Autowired
    CompetencyService competencyService;

    @GetMapping("/comp-dict")
    private String viewCompetencyDict(Model model) {
        List<Competency> listCompetency = competencyService.getListCompetency();
        model.addAttribute("listCompetency", listCompetency);
        return "comp-hr";
    }

    @GetMapping("/comp-dict/detail")
    private String detailTrainingCatalog(@RequestParam(value = "idComp") Long idComp, Model model) {
        Competency competency = competencyService.getCompetencyById(idComp);
        model.addAttribute("competency", competency);

        return "comp-detail";
    }

    @GetMapping("/comp-dict/add")
    public String addCompetencyFormPage(Model model) {
        Competency competency = competencyService.prepareCompetencyTemplate();
        model.addAttribute("competency", competency);

        return "form-add-comp";
    }

    @PostMapping(value = "/comp-dict/add", params = {"save"})
    public String addCompetencySubmit(@ModelAttribute Competency competency, Model model) {
        Competency processedCompetency = competencyService.processCompetencyData(competency);
        competencyService.addCompetency(processedCompetency);
        model.addAttribute("listCompetency", competencyService.getListCompetency());
        model.addAttribute("successtext","Competency " + competency.getNama() + " berhasil ditambah.");

        return "comp-hr";
    }

    @GetMapping("/comp-dict/delete/{idComp}")
    public String deleteCompetency(@PathVariable(value = "idComp") Long idComp, Model model) {
        Competency competency = competencyService.getCompetencyById(idComp);
        competencyService.deleteCompetency(competency);

        model.addAttribute("listCompetency", competencyService.getListCompetency());

        return "comp-hr";
    }

    @GetMapping("/comp-dict/update/{idComp}")
    public String updateCompetencyFormPage(@PathVariable(value = "idComp") Long idComp, Model model) {
        Competency competency = competencyService.getCompetencyById(idComp);
        model.addAttribute("competency", competency);

        return "form-update-comp";
    }

    @PostMapping(value = "/comp-dict/update", params = {"save"})
    public String updateCompetencySubmit(@ModelAttribute Competency competency, Model model, RedirectAttributes redirectAttributes) {
        competencyService.processCompetencyData(competency);
        competencyService.addCompetency(competency);

        model.addAttribute("successtext","Competency " + competency.getNama() + " berhasil diubah.");

        model.addAttribute("listCompetency", competencyService.getListCompetency());
        return "comp-hr";
    }
}