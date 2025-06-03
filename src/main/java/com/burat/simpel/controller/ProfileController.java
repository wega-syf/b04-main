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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {
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

    @GetMapping(value="/profile")
    private String viewProfile(Model model) {
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

        return "profile";
    }
    @GetMapping(value ="/profile/edit/{uuid}")
    private String editUserAccount(Model model, @PathVariable String uuid, HttpServletRequest request){
        // Get model from db
        Optional<AdminModel> adminModel = adminService.getByUuid(uuid);
        Optional<AssessorModel> assessorModel = assessorService.getByUuid(uuid);
        Optional<ExecutiveModel> executiveModel = executiveService.getByUuid(uuid);
        Optional<UserModel> userModel = userService.getByUuid(uuid);

        // Switch it to an AccountModel object
        AccountModel modelAkun = new AccountModel();
        if (adminModel.isPresent()){
            BeanUtils.copyProperties(adminModel.get(),modelAkun);
            model.addAttribute("modelAkun",modelAkun);

            // Warning attribute that's true if the default admin account is detected
            // THIS IS TO PREVENT A WEIRD SITUATION WHERE THERE IS NO DEFAULT ADMIN ACCOUNT
            if (adminModel.get().getUsername().equals("admin")){
                model.addAttribute("defaultAccountWarning",true);
            }
            // Warning attribute that's true if the current account is logged in
            // THIS IS TO PREVENT A WEIRD SITUATION WHERE THE CURRENT ADMIN IS SUDDENLY CHANGED TO NOT ADMIN
            if (request.getRemoteUser().equals(adminModel.get().getUsername())){
                model.addAttribute("loggedInAccountWarning",true);
            }
        }if (assessorModel.isPresent()){
            BeanUtils.copyProperties(assessorModel.get(),modelAkun);
            model.addAttribute("modelAkun",modelAkun);
        }if (executiveModel.isPresent()){
            BeanUtils.copyProperties(executiveModel.get(),modelAkun);
            model.addAttribute("modelAkun",modelAkun);
        }if (userModel.isPresent()){
            BeanUtils.copyProperties(userModel.get(),modelAkun);
            model.addAttribute("modelAkun",modelAkun);
        }

        // Get access to the listDivisi and listTitle
        List<DivisiModel> listDivisi = divisiService.getAllDivisi();
        List<TitleModel> listTitle = titleService.getAllTitle();

        model.addAttribute("listDivisi", listDivisi);
        model.addAttribute("listTitle", listTitle);


        return "profile-edit";
    }
    @PostMapping(value ="profile/edit/submit/{uuid}",params = {"currentRole", "currentUsername"})
    private String submitEditingUserAccount(Model model,@PathVariable String uuid, @RequestParam String currentRole,@RequestParam String currentUsername, @ModelAttribute AccountModel modelAccount, RedirectAttributes redirectAttributes ){
        if(accountService.doesAccountExistsByUsername(modelAccount.getUsername())) {
            // If it's not the same account, then it's an error
                if (!currentUsername.equals(modelAccount.getUsername())){
                    redirectAttributes.addFlashAttribute("errortext","Akun lain dengan username " + modelAccount.getUsername() + " sudah terdapat pada sistem. Silahkan ganti username atau role");
                    return "redirect:/profile/edit/"+uuid;
                }
        }

        // Condition's clear
        // Delete current then register a new one
        accountService.deleteAccountDependsOnRoleByUuid(uuid,currentRole);
        // Register the new model, without enrypting password.
        accountService.registerUtil(modelAccount);
        redirectAttributes.addFlashAttribute("successtext","Akun " + modelAccount.getUsername() + " berhasil diubah");
        return "redirect:/profile";

    }
}
