package com.burat.simpel.controller;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private DivisiService divisiService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ExecutiveService executiveService;

    @Autowired
    private AssessorService assessorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping(value="/register")
    private String registerFormPage(Model model) {
        AccountModel account = new AccountModel();
        List<DivisiModel> listDivisi = divisiService.getAllDivisi();
        List<TitleModel> listTitle = titleService.getAllTitle();

        model.addAttribute("account", account);
        model.addAttribute("listDivisi", listDivisi);
        model.addAttribute("listTitle", listTitle);

        return "form-register";
    }

    @PostMapping(value="/register")
    private String registerSubmit(@ModelAttribute AccountModel account, Model model, RedirectAttributes redirectAttributes) {
//        List<String> listUsername = new ArrayList<>();
//        listUsername.addAll(adminService.getUsername());
//        listUsername.addAll(assessorService.getUsername());
//        listUsername.addAll(executiveService.getUsername());
//        listUsername.addAll(userService.getUsername());
//
//        if (listUsername.contains(account.getUsername())) {
////            redirectAttributes.addFlashAttribute("successtext","Username " + account.getUsername() + " sudah terdaftar.");
//
//            List<DivisiModel> listDivisi = divisiService.getAllDivisi();
//            List<TitleModel> listTitle = titleService.getAllTitle();
//
//            model.addAttribute("errortext","Username " + account.getUsername() + " sudah terdaftar.");
//            model.addAttribute("account", account);
//            model.addAttribute("listDivisi", listDivisi);
//            model.addAttribute("listTitle", listTitle);
//            model.addAttribute("listUsername", listUsername);
//
//            return "form-register";
//        }
//
//        accountService.register(account);  // Handles all the steps in register, with password encryption
//        redirectAttributes.addFlashAttribute("successtext","Akun " + account.getUsername() + " berhasil didaftarkan");
//        return "redirect:/account-management";
        // Check if exist in db first, if already exist then return a fail notif
        if (accountService.doesAccountExistsByUsername(account.getUsername())){
            redirectAttributes.addFlashAttribute("errortext","Akun '" + account.getUsername() + "' sudah terdapat dalam sistem");
            return "redirect:/register";

        } else {
        // If new, then create a new entry
            accountService.register(account);  // Handles all the steps in register, with password encryption
            redirectAttributes.addFlashAttribute("successtext","Akun '" + account.getUsername() + "' berhasil didaftarkan");
            return "redirect:/account-management";
        }
    }



}
