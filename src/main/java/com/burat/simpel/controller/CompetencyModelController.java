package com.burat.simpel.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.burat.simpel.model.Competency;
import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.CompetencyModel;
import com.burat.simpel.model.TitleModel;
import com.burat.simpel.repository.TitleDb;
import com.burat.simpel.service.CompetencyModelService;
import com.burat.simpel.service.CompetencyService;
import com.burat.simpel.service.TitleService;

@Controller
public class CompetencyModelController {
    @Autowired
    CompetencyModelService competencyModelService;

    @Autowired
    CompetencyService competencyService;

    @Autowired
    TitleService titleService;

    @GetMapping("/competency-model")
    public String viewallCompetencyModel(Model model) {
        List<CompetencyModel> listCompetencyModel = competencyModelService.getAllCompetencyModel();
        model.addAttribute("listCompetencyModel", listCompetencyModel);
        return "viewall-competency-model";
    }

    @GetMapping("/competency-model/{id}")
    public String viewCompetencyLevel(@PathVariable Long id, Model model) {
        CompetencyModel competencyModel = competencyModelService.getCompetencyModelById(id);
        model.addAttribute("listCompetencyLevel", competencyModel.getListCompetencyLevel());
        model.addAttribute("idModel", id);
        model.addAttribute("title", competencyModel.getTitle());
        model.addAttribute("deskripsi", competencyModel.getDeskripsi());
        return "view-competency-model";
    }

    @GetMapping(value = "/competency-model/add")
    public String addCompetencyModel(Model model) {
        CompetencyModel competencyModel = new CompetencyModel();
        CompetencyLevel competencyLevel = new CompetencyLevel();
        List<CompetencyLevel> listCompetencyLevel = new ArrayList<>();
        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        List<TitleModel> listTitle = titleService.getSomeTitle();

        listCompetencyLevel.add(competencyLevel);
        competencyModel.setListCompetencyLevel(listCompetencyLevel);

        model.addAttribute("listTitle", listTitle);
        model.addAttribute("competencyModel", competencyModel);
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);
        return "form-add-competency-model";
    }

    @PostMapping(value = "/competency-model/add", params = { "addRowCompetency" })
    private String addRowCompetencyModel(@ModelAttribute CompetencyModel competencyModel, Model model) {
        if (competencyModel.getListCompetencyLevel() == null || competencyModel.getListCompetencyLevel().size() == 0) {
            competencyModel.setListCompetencyLevel(new ArrayList<>());
        }
        competencyModel.getListCompetencyLevel().add(new CompetencyLevel());
        model.addAttribute("competencyModel", competencyModel);

        List<TitleModel> listTitle = titleService.getAllTitle();
        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        model.addAttribute("listTitle", listTitle);
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);
        return "form-add-competency-model";
    }

    @PostMapping(value = "/competency-model/add", params = { "deleteRowCompetency" })
    private String deleteRowCompetencyModel(@ModelAttribute CompetencyModel competencyModel,
            @RequestParam("deleteRowCompetency") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        competencyModel.getListCompetencyLevel().remove(rowId.intValue());
        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        List<TitleModel> listTitle = titleService.getAllTitle();
        model.addAttribute("listTitle", listTitle);
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);
        model.addAttribute("competencyModel", competencyModel);
        return "form-add-competency-model";
    }

    @PostMapping(value = "/competency-model/add", params = { "save" })
    public String addCompetencyModelSubmit(@ModelAttribute CompetencyModel competencyModel, Model model, RedirectAttributes redirectAttributes) {
        if (competencyModel.getListCompetencyLevel() == null || competencyModel.getListCompetencyLevel().size() == 0) {
            competencyModel.setListCompetencyLevel(new ArrayList<>());
        }
        competencyModel.setListCompetencyLevel(
                competencyModelService.extractCompetencyLevel(competencyModel.getListCompetencyLevel()));
        competencyModelService.addCompetencyModel(competencyModel);

        List<CompetencyModel> listCompetencyModel = competencyModelService.getAllCompetencyModel();
        model.addAttribute("listCompetencyModel", listCompetencyModel);
        redirectAttributes.addFlashAttribute("successtext", "Selamat! Competency Model berhasil ditambahkan");
        return "redirect:/competency-model";
    }

    @GetMapping("/competency-model/delete/{id}")
    public String deleteCompetencyModel(@PathVariable(value = "id") Long id, Model model) {
        CompetencyModel competencyModel = competencyModelService.getCompetencyModelById(id);
        competencyModelService.deleteCompetencyModel(competencyModel);
        model.addAttribute("listCompetencyModel", competencyModelService.getAllCompetencyModel());
        return "redirect:/competency-model";
    }

    @GetMapping("/competency-model/update/{id}")
    public String updateCompetencyModel(@PathVariable(value = "id") Long id, Model model) {
        CompetencyModel competencyModel = competencyModelService.getCompetencyModelById(id);
        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        List<TitleModel> listTitle = titleService.getAllTitle();
        model.addAttribute("listTitle", listTitle);
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);
        model.addAttribute("competencyModel", competencyModel);
        return "form-update-competency-model";
    }

    @PostMapping(value = "/competency-model/update", params = { "addRow" })
    public String addRowCompetencyModelUpdate(@ModelAttribute CompetencyModel competencyModel, Model model) {
        if (competencyModel.getListCompetencyLevel() == null || competencyModel.getListCompetencyLevel().size() == 0) {
            competencyModel.setListCompetencyLevel(new ArrayList<>());
        }
        competencyModel.getListCompetencyLevel().add(new CompetencyLevel());
        competencyModel.setTitle(titleService.getTitleById(competencyModel.getTitle().getIdTitle()));
        model.addAttribute("competencyModel", competencyModel);

        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);

        return "form-update-competency-model";
    }

    @PostMapping(value = "/competency-model/update", params = { "deleteRow" })
    private String deleteRowCompetencyModelUpdate(@ModelAttribute CompetencyModel competencyModel,
            @RequestParam("deleteRow") Integer row, Model model) {
        final Integer rowId = Integer.valueOf(row);
        competencyModel.getListCompetencyLevel().remove(rowId.intValue());
        List<Competency> listCompetencyExisting = competencyService.getListCompetency();
        competencyModel.setTitle(titleService.getTitleById(competencyModel.getTitle().getIdTitle()));
        model.addAttribute("title", competencyModel.getTitle());
        model.addAttribute("listCompetencyExisting", listCompetencyExisting);
        model.addAttribute("competencyModel", competencyModel);
        return "form-update-competency-model";
    }

    @PostMapping(value = "/competency-model/update", params = { "save" })
    public String updateCompetencyModelSubmit(@ModelAttribute CompetencyModel competencyModel, Model model, RedirectAttributes redirectAttributes) {
        if (competencyModel.getListCompetencyLevel() == null || competencyModel.getListCompetencyLevel().size() == 0) {
            competencyModel.setListCompetencyLevel(new ArrayList<>());
        }
        competencyModel.setListCompetencyLevel(competencyModelService.extractCompetencyLevel(competencyModel.getListCompetencyLevel()));
        competencyModelService.addCompetencyModel(competencyModel);
        model.addAttribute("listCompetencyModel", competencyModel.getListCompetencyLevel());
        redirectAttributes.addFlashAttribute("successtext", "Selamat! Competency Model berhasil diubah");
        return "redirect:/competency-model";
    }
}
