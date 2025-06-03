package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountManagementController {
    @Autowired
    AdminService adminService;
    @Autowired
    AssessorService assessorService;
    @Autowired
    ExecutiveService executiveService;
    @Autowired
    UserService userService;

    @Autowired
    DivisiService divisiService;

    @Autowired
    TitleService titleService;

    @Autowired
    AccountService accountService;

    final String BASE_URL = "account-management";


    @GetMapping(BASE_URL)
    private String accountList(Model model){
        List<AdminModel> listAdmin = adminService.getAllAdmin();
        List<AssessorModel> listAssessor = assessorService.getAllAssessor();
        List<ExecutiveModel> listExecutive = executiveService.getAllExecutive();
        List<UserModel> listUser = userService.getAllUser();
        Integer size = listAdmin.size() + listAssessor.size() + listExecutive.size() + listUser.size();
        String status = "all";

        model.addAttribute("listAdmin",listAdmin);
        model.addAttribute("listAssessor",listAssessor);
        model.addAttribute("listExecutive",listExecutive);
        model.addAttribute("listUser",listUser);
        model.addAttribute("size",size);
        model.addAttribute("status",status);

        return "account-list";
    }@GetMapping(BASE_URL+"/admin")
    private String adminAccountList(Model model){
        List<AdminModel> listAdmin = adminService.getAllAdmin();
        String status = "admin";

        Integer size = listAdmin.size();

        model.addAttribute("listAdmin",listAdmin);
        model.addAttribute("size",size);
        model.addAttribute("status",status);

        return "account-list";
    }@GetMapping(BASE_URL+"/assessor")
    private String assessorAccountList(Model model){
        List<AssessorModel> listAssessor = assessorService.getAllAssessor();
        String status = "assessor";

        Integer size = listAssessor.size();

        model.addAttribute("listAssessor",listAssessor);
        model.addAttribute("size",size);
        model.addAttribute("status",status);

        return "account-list";
    }@GetMapping(BASE_URL+"/executive")
    private String execAccountList(Model model){
        List<ExecutiveModel> listExecutive = executiveService.getAllExecutive();
        String status = "executive";

        Integer size = listExecutive.size();

        model.addAttribute("listExecutive",listExecutive);
        model.addAttribute("size",size);
        model.addAttribute("status",status);

        return "account-list";
    }@GetMapping(BASE_URL+"/user")
    private String userAccountList(Model model){
        List<UserModel> listUser = userService.getAllUser();
        String status = "user";

        Integer size = listUser.size();

        model.addAttribute("listUser",listUser);
        model.addAttribute("size",size);
        model.addAttribute("status",status);

        return "account-list";
    }

    @GetMapping(BASE_URL+"/delete/admin/{uuid}")
    private String adminDeleteAccount(Model model, RedirectAttributes redirectAttributes, @PathVariable String uuid){
        redirectAttributes.addFlashAttribute("deletestatus","success");
        adminService.deleteAccountByUuid(uuid);

        return "redirect:/account-management";
    }@GetMapping(BASE_URL+"/delete/assessor/{uuid}")
    private String assessorDeleteAccount(Model model, RedirectAttributes redirectAttributes, @PathVariable String uuid){
        redirectAttributes.addFlashAttribute("deletestatus","success");
        assessorService.deleteAccountByUuid(uuid);

        return "redirect:/account-management";
    }@GetMapping(BASE_URL+"/delete/executive/{uuid}")
    private String executiveDeleteAccount(Model model, RedirectAttributes redirectAttributes, @PathVariable String uuid){
        redirectAttributes.addFlashAttribute("deletestatus","success");
        executiveService.deleteAccountByUuid(uuid);

        return "redirect:/account-management";
    }@GetMapping(BASE_URL+"/delete/user/{uuid}")
    private String userDeleteAccount(Model model, RedirectAttributes redirectAttributes, @PathVariable String uuid){
        redirectAttributes.addFlashAttribute("deletestatus","success");
        userService.deleteAccountByUuid(uuid);

        return "redirect:/account-management";
    }
    @GetMapping(BASE_URL+"/edit/{uuid}")
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


        return "account-edit";
    }
    @PostMapping(value = BASE_URL+"/edit/submit/{uuid}",params = {"currentRole", "currentUsername"})
    private String submitEditingUserAccount(Model model,@PathVariable String uuid, @RequestParam String currentRole,@RequestParam String currentUsername, @ModelAttribute AccountModel modelAccount, RedirectAttributes redirectAttributes ){
        if(accountService.doesAccountExistsByUsername(modelAccount.getUsername())) {
            // If it's not the same account, then it's an error
                if (!currentUsername.equals(modelAccount.getUsername())){
                    redirectAttributes.addFlashAttribute("errortext","Akun lain dengan username " + modelAccount.getUsername() + " sudah terdapat pada sistem. Silahkan ganti username atau role");
                    return "redirect:/account-management/edit/"+uuid;
                }
        }

        // Condition's clear
        // Delete current then register a new one
        accountService.deleteAccountDependsOnRoleByUuid(uuid,currentRole);
        // Register the new model, without enrypting password.
        accountService.registerUtil(modelAccount);
        redirectAttributes.addFlashAttribute("successtext","Akun " + modelAccount.getUsername() + " berhasil diubah");
        return "redirect:/account-management";


    }
}
