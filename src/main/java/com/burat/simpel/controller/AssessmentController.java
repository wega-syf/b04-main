package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AssessmentController {
    final String BASE_URL = "/do-assessment";
    final String BASE_URL_HIST = "/history-assessment";

    @Autowired
    UserService userService;

    @Autowired
    AssessorService assessorService;
    @Autowired
    AssessmentService assessmentService;
    @Autowired
    AssessmentLevelService assessmentLevelService;

    @Autowired
    CompetencyLevelService competencyLevelService;

    @Autowired
    EventPeriodService eventPeriodService;

    @Autowired
    ReviewAssessmentService reviewAssessmentService;

    @GetMapping(BASE_URL)
    private String selectUser(Model model, RedirectAttributes redirectAttributes){
        if (eventPeriodService.findActiveAssessmentPeriod().isPresent()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            AssessorModel assessorModel = assessorService.getByUsername(currentPrincipalName);
            EventPeriodModel eventPeriodModel = eventPeriodService.findActiveAssessmentPeriod().get();

            List<UserModel> listuserDone = assessmentService.getAssessedUser(assessorModel, eventPeriodModel);
            List<UserModel> listuserToAssess = assessmentService.getNotAssessedUser(assessorModel, eventPeriodModel);
            model.addAttribute("listuserDone",listuserDone);
            model.addAttribute("listuserToAssess",listuserToAssess);
            model.addAttribute("eventPeriod", eventPeriodService.findActiveAssessmentPeriod().get());
            model.addAttribute("activeAssessmentPeriod",true);

            return "assessment-list-user";
        } else {
            model.addAttribute("activeAssessmentPeriod",false);
            return "assessment-list-user";
        }

    }

    @GetMapping(BASE_URL+"/{uuid}")
    private String assessmentForm(Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest, @PathVariable String uuid){
        if (eventPeriodService.findActiveAssessmentPeriod().isPresent()) {
            model.addAttribute("activeAssessmentPeriod",true);

           Optional<UserModel> userModel = userService.getByUuid(uuid);
            if (!userModel.isPresent()){
                redirectAttributes.addFlashAttribute("errortext", "User dengan uuid " +uuid +" tidak ada");
                return "redirect:"+BASE_URL;
            }
            UserModel userModelget = userModel.get(); 


            // Create empty assessment model
            AssessmentModel assessmentModel = new AssessmentModel();
            AssessorModel currentAssessor = assessorService.getByUsername(httpServletRequest.getRemoteUser());

            if (currentAssessor == null){
                redirectAttributes.addFlashAttribute("errortext", "Akun ini bukan assessor, tidak bisa melakukan assessment");
                return "redirect:"+BASE_URL;
            }

            // Attribute to show Event Period

            assessmentModel.setAssessor(currentAssessor);
            assessmentModel.setUser(userModelget);
            assessmentModel.setDate(LocalDate.now());
            assessmentModel.setEvent(eventPeriodService.findActiveAssessmentPeriod().get());
            assessmentModel.setListAssessment(new ArrayList<>());

            // Check duplicate assessor user and time
            if (assessmentService.checkDuplicate(assessmentModel)){
                redirectAttributes.addFlashAttribute("errortext", "Anda sudah pernah melakukan assessment ke user  " + "'" + assessmentModel.getUser().getUsername() + "'" + " untuk periode ini");
                return "redirect:"+BASE_URL;
            }

            // Max Level for each comptency
            List<Integer> maxLevelOfCompetency = new ArrayList<>();
            // Assessment level empty list
            List<AssessmentLevelModel> listAssessmentLevel = new ArrayList<>();


            // Get all Competency of this user
            List<CompetencyModel> listCompetency = userModelget.getTitleModel().getListCompetencyModel();
            if (listCompetency.isEmpty()){
                // Error if current user doesn't have competency model
                redirectAttributes.addFlashAttribute("errortext", "User dengan uuid " +uuid +" tidak memiliki competency model, buat terlebih dahulu");
                return "redirect:"+BASE_URL;
            }

            // Append new of AssessmentLevel objects according to already existing competency model
            for (CompetencyLevel x : listCompetency.get(0).getListCompetencyLevel()){
                // setup the new object
                AssessmentLevelModel assessmentLevelModel = new AssessmentLevelModel();
                assessmentLevelModel.setAssessment(assessmentModel);
                assessmentLevelModel.setCompetencyLevel(x);
                assessmentLevelModel.setGap(Long.valueOf(x.getLevel())); // input the current level of competency
                assessmentLevelModel.setResult(0L); // default is 0

                // Append new entry of max level for each competency
                maxLevelOfCompetency.add(competencyLevelService.countLevelOfCompetency(Long.valueOf(x.getLevel())));
                // save to the temp list
                listAssessmentLevel.add(assessmentLevelModel);
            }
            // System.out.println(maxLevelOfCompetency);
            // save list to the model
            assessmentModel.setListAssessment(listAssessmentLevel);
            // save model
            model.addAttribute("usermodel",userModelget);
            model.addAttribute("assessment",assessmentModel);
            model.addAttribute("eventPeriod",eventPeriodService.findActiveAssessmentPeriod().get());
            model.addAttribute("listMaxLevelCompetency",maxLevelOfCompetency);

            return "assessment-form";
        } else {
            model.addAttribute("activeAssessmentPeriod",false);
            return "redirect:"+BASE_URL;
        }


    }

    @PostMapping(value = BASE_URL+ "/{uuid}", params = {"save"})
    public String assessmentFormSubmit(@ModelAttribute @Valid AssessmentModel assessmentModel, Model model, RedirectAttributes redirectAttributes){
        if (eventPeriodService.findActiveAssessmentPeriod().isPresent()) {
            redirectAttributes.addFlashAttribute("activeAssessmentPeriod",true);

            // Check duplicate assessor user and time
            if (assessmentService.checkDuplicate(assessmentModel)){
                redirectAttributes.addFlashAttribute("errortext", "Anda sudah pernah melakukan assessment ke user  " + "'" + assessmentModel.getUser().getUsername() + "'" + " untuk periode ini");
                return "redirect:"+BASE_URL;
            }
            // Check whether the all competency levels have been assessed
            for (AssessmentLevelModel x : assessmentModel.getListAssessment()){
                if (x.getResult() == null || x.getResult() == 0){
                    redirectAttributes.addFlashAttribute("errortext", "Tidak boleh ada nilai level yang kosong");
                    return "redirect:"+BASE_URL+"/"+assessmentModel.getUser().getAccountUuid();
                }
            }

            // Save the model
            assessmentService.add(assessmentModel);
            // Setup for every single assessmentLevel
            for (AssessmentLevelModel x : assessmentModel.getListAssessment()){
                x.setAssessment(assessmentModel);

                // Setting Gap
                x.setGap(x.getResult() - x.getGap());

                // save the model
                assessmentLevelService.add(x);
            }
            reviewAssessmentService.setReview(assessmentModel);
            // success notif
            redirectAttributes.addFlashAttribute("successtext", "User dengan username "+ assessmentModel.getUser().getUsername()+" sudah berhasil dilakukan assessment");
            return "redirect:"+BASE_URL;
        } else {
            model.addAttribute("activeAssessmentPeriod",false);
            return "redirect:"+BASE_URL;
        }


    }

    @GetMapping(BASE_URL_HIST)
    public String viewHistoryAssessmentAll(Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean isAssessor = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("assessor"));
        List<EventPeriodModel> assessmentPeriodList = eventPeriodService.getEventPeriodByJenis("assessment");

        if (isAssessor){
            AssessorModel assessorModel = assessorService.getByUsername(currentPrincipalName);
            List<AssessmentModel> assessmentModelsByAssessor = assessmentService.getAssessmentModelByAssessor(assessorModel.getAccountUuid());
            model.addAttribute("assessorModel",assessorModel);
            model.addAttribute("eventPeriodAssessmentList", assessmentPeriodList);
            model.addAttribute("activeAssessmentPeriod", eventPeriodService.findActiveAssessmentPeriod().isPresent());
            model.addAttribute("assessmentListByAssessor", assessmentModelsByAssessor);
            return "assessment-history";
        }

        List<AssessorModel> assessorModelList = assessorService.getAllAssessor();
        List<AssessmentModel> assessmentModelsListAll = assessmentService.getAllAssessmentModel();

        model.addAttribute("eventPeriodAssessmentList", assessmentPeriodList);
        model.addAttribute("assessorListAll",assessorModelList);
        model.addAttribute("assessmentListAll",assessmentModelsListAll);
        model.addAttribute("activeAssessmentPeriod", eventPeriodService.findActiveAssessmentPeriod().isPresent());
        return "assessment-history";
    }

    @GetMapping(BASE_URL_HIST+"/assessor-filter")
    public String viewHistoryAssessmentFilterAssessor(Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "eventId") Long eventPeriodId){
        Optional<EventPeriodModel> modelEvent = eventPeriodService.getEventPeriodById(eventPeriodId);
        if (!modelEvent.isPresent()){
            redirectAttributes.addFlashAttribute("errortext", "Id Event Periode yang dipiih tidak valid");
            return "redirect:"+BASE_URL_HIST;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AssessorModel assessorModel = assessorService.getByUsername(currentPrincipalName);

        List<AssessmentModel> assessmentModelEventAssessor = assessmentService.getAssessmentModelByPeriodAndAssessor(eventPeriodId, assessorModel.getAccountUuid());
        redirectAttributes.addFlashAttribute("assessmentListQueryAssessor",assessmentModelEventAssessor);
        redirectAttributes.addFlashAttribute("selectedEvent", modelEvent.get());
        redirectAttributes.addFlashAttribute("assessorModel", assessorModel);
        return "redirect:"+BASE_URL_HIST;
    }

    @GetMapping(BASE_URL_HIST+"/admin-filter")
    public String viewHistoryAssessmentFilter(Model model, RedirectAttributes redirectAttributes, @RequestParam(name = "eventId") Long eventPeriodId, @RequestParam(name = "assessorUuid",required = false) String assessorUuid){
        Optional<EventPeriodModel> modelEvent = eventPeriodService.getEventPeriodById(eventPeriodId);
        if (!modelEvent.isPresent()){
            redirectAttributes.addFlashAttribute("errortext", "Id Event Periode yang dipiih tidak valid");
            return "redirect:"+BASE_URL_HIST;
        }

        if (assessorUuid == null){
            // searching by event only
            List<AssessmentModel> assessmentModelOnlyEvent = assessmentService.getAssessmentModelByPeriod(eventPeriodId);
            redirectAttributes.addFlashAttribute("assessmentListQuery",assessmentModelOnlyEvent);
            redirectAttributes.addFlashAttribute("selectedEvent", modelEvent.get());
        } else {
            // searching by event and assessor
            Optional<AssessorModel> assessorModel = assessorService.getByUuid(assessorUuid);
            if (!assessorModel.isPresent()){
                redirectAttributes.addFlashAttribute("errortext", "Id Assessor yang dipiih tidak valid");
                return "redirect:"+BASE_URL_HIST;
            }

            List<AssessmentModel> assessmentModelEventAssessor = assessmentService.getAssessmentModelByPeriodAndAssessor(eventPeriodId, assessorUuid);
            redirectAttributes.addFlashAttribute("assessmentListQuery",assessmentModelEventAssessor);
            redirectAttributes.addFlashAttribute("selectedEvent", modelEvent.get());
            redirectAttributes.addFlashAttribute("selectedAssessor", assessorModel.get());

        }
        return "redirect:"+BASE_URL_HIST;
    }
    @GetMapping(BASE_URL_HIST+"/detail/{id}")
    public String viewDetailHistoryAssessment(Model model, RedirectAttributes redirectAttributes, @PathVariable Long id){
        Optional<AssessmentModel> assessmentModel = assessmentService.getAssessmentModelById(id);
        // salah Id
        if (assessmentModel.isEmpty()){
            redirectAttributes.addFlashAttribute("errortext", "Id Assessment tidak valid");
            return "redirect:"+BASE_URL_HIST;
        }
        // assessor tidak bisa melihat penilaian yang diberikan assessor lain
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        boolean isAssessor = authentication.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("assessor"));
        if (isAssessor){
            AssessorModel assessorModel= assessorService.getByUsername(currentPrincipalName);
            // error if different assessor id
            if (!assessmentModel.get().getAssessor().getAccountUuid().equals(assessorModel.getAccountUuid())){
                redirectAttributes.addFlashAttribute("errortext", "Tidak bisa melihat assessment yang dinilai oleh assessor lain");
                return "redirect:"+BASE_URL_HIST;
            }
        }

        model.addAttribute("assessmentModel",assessmentModel.get());
        return "assessment-history-detail";
    }

}
