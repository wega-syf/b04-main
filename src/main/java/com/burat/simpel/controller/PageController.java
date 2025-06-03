package com.burat.simpel.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;

import java.util.List;

@Controller
public class PageController {
    @Autowired
    ProfileService profileService;

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    AssessorService assessorService;
    
    @Autowired
    ExecutiveService executiveService;

    @Autowired
    DivisiService divisiService;

    @Autowired
    TitleService titleService;

    @Autowired
    AccountService accountService;

    @Autowired
    TrainingPlanService trainingPlanService;

    @GetMapping("/")
    public String homePage(Model model){
        UserModel akunUser = profileService.getUser();        
        if (akunUser != null){
            model.addAttribute("akun", akunUser);
        }
        else{
            AdminModel akunAdmin = profileService.getAdmin();
            if (akunAdmin != null){
                model.addAttribute("akun", akunAdmin);
            }
            else{
                ExecutiveModel akunExecutive = profileService.getExecutive();
                if (akunExecutive != null){
                    model.addAttribute("akun", akunExecutive);
                }
                else{
                    AssessorModel akunAssessor = profileService.getAssessor();
                    model.addAttribute("akun", akunAssessor);
                }
            }
        }
        model.addAttribute("Done", trainingPlanService.getJumlahTrainingDone());
        model.addAttribute("Active", trainingPlanService.getJumlahTrainingActive());
        model.addAttribute("Confirmed", trainingPlanService.getJumlahTrainingConfirmed());
        return "home";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "form-login";
    }
}
