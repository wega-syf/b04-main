package com.burat.simpel.controller;

import java.security.Principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.burat.simpel.model.*;
import com.burat.simpel.service.*;
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

import com.burat.simpel.model.TrainingModel;

@Controller
public class TrainingPlanController {
    @Autowired
    TrainingPlanService trainingPlanService;

    @Autowired
    EventPeriodService eventPeriodService;

    @Autowired
    TrainingTakenByUserService trainingTakenByUserService;

    @Autowired
    UserService userService;

    @Autowired
    TrainingService trainingService;

    @GetMapping("/training-plan")
    public String viewallTrainingPlan(Model model) {
        List<TrainingPlanModel> listTrainingPlan = trainingPlanService.getAllTrainingPlan();
        model.addAttribute("activeTrainingPeriod",eventPeriodService.findActiveTrainingPeriod().isPresent());
        model.addAttribute("listTrainingPlan", listTrainingPlan);
        return "viewall-training-plan";
    }

    @GetMapping("/training-plan/{id}")
    public String viewTrainingPlan(@PathVariable Long id, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
        model.addAttribute("listUserTrainingPlan", trainingPlanModel.getUserInTrainingPlan());
        model.addAttribute("trainingPlanModel",trainingPlanModel);
        model.addAttribute("idTrainingPlan", id);
        model.addAttribute("idTraining", trainingPlanModel.getIdTraining());
        model.addAttribute("nama", trainingPlanModel.getNama());
        model.addAttribute("deskripsi", trainingPlanModel.getDeskripsi());
        model.addAttribute("dateStart", trainingPlanModel.getDateStart());
        model.addAttribute("dateEnd", trainingPlanModel.getDateEnd());
        model.addAttribute("budget", trainingPlanModel.getBudget());
        model.addAttribute("status", trainingPlanModel.getStatus());
        model.addAttribute("eventPeriod",trainingPlanModel.getEventPeriodModel());
        model.addAttribute("activeTrainingPeriod",eventPeriodService.findActiveTrainingPeriod().isPresent());
        return "view-training-plan";
    }

    @GetMapping(value = "/training-plan/add")
    public String addTrainingPlan(Model model, RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("activeTrainingPeriod",true);

            TrainingPlanModel trainingPlanModel = new TrainingPlanModel();
            UserModel user = new UserModel();
            TrainingPlanTakenByUserModel trainingPlanTakenByUserModel = new TrainingPlanTakenByUserModel();
            trainingPlanTakenByUserModel.setUserModel(user);

            List<TrainingPlanTakenByUserModel> listUserTaken = new ArrayList<>();
            List<UserModel> listUserExisting = userService.getAllUser();
            List<TrainingModel> listTraining = trainingService.getListTraining();

            listUserTaken.add(trainingPlanTakenByUserModel);
            trainingPlanModel.setUserInTrainingPlan(listUserTaken);

            model.addAttribute("listTraining", listTraining);
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            model.addAttribute("listUserExisting", listUserExisting);
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());

            return "form-add-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa melakukan penambahan karena ini bukan periode training");
            return "redirect:/training-plan";
        }
    }

    @PostMapping(value = "/training-plan/add", params = { "addRowUser" })
    private String addRowTrainingPlan(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model, RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            model.addAttribute("activeTrainingPeriod",true);

            // method starts here
            if (trainingPlanModel.getUserInTrainingPlan() == null
                    || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
                trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
            }
            TrainingPlanTakenByUserModel trainingPlanUserModel = new TrainingPlanTakenByUserModel();
            trainingPlanUserModel.setUserModel(new UserModel());
            trainingPlanModel.getUserInTrainingPlan().add(trainingPlanUserModel);
            model.addAttribute("trainingPlanModel", trainingPlanModel);

            List<TrainingModel> listTraining = trainingService.getListTraining();
            List<UserModel> listUserExisting = userService.getAllUser();
            model.addAttribute("listTraining", listTraining);
            model.addAttribute("listUserExisting", listUserExisting);
            return "form-add-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa melakukan penambahan karena ini bukan periode training");
            return "redirect:/training-plan";
        }
    }

    @PostMapping(value = "/training-plan/add", params = { "deleteRowTraining" })
    private String deleteRowTrainingPlan(@ModelAttribute TrainingPlanModel trainingPlanModel, RedirectAttributes redirectAttributes,
            @RequestParam("deleteRowTraining") Integer row, Model model) {

        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            model.addAttribute("activeTrainingPeriod",true);

            // method starts here
            final Integer rowId = Integer.valueOf(row);
            trainingPlanModel.getUserInTrainingPlan().remove(rowId.intValue());

            List<TrainingModel> listTraining = trainingService.getListTraining();
            List<UserModel> listUserExisting = userService.getAllUser();

            model.addAttribute("listTraining", listTraining);
            model.addAttribute("listUserExisting", listUserExisting);
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            return "form-add-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa melakukan penambahan karena ini bukan periode training");
            return "redirect:/training-plan";
        }
    }

    @PostMapping(value = "/training-plan/add", params = { "save" })
    public String addTrainingPlanSubmit(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model,
            RedirectAttributes redirectAttributes) {

        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            redirectAttributes.addFlashAttribute("activeTrainingPeriod",true);

            // Validasi tanggal di dalam range Event
            if (!trainingPlanService.TrainingDateValidationCheckWithEvent(trainingPlanModel, eventPeriodService.findActiveTrainingPeriod().get())){
                redirectAttributes.addFlashAttribute("errortext","Tanggal berlangsung training harus berada di dalam periode event yang berlaku pada saat ini");
                return "redirect:/training-plan/add";
            }

            // method starts heer
            if (trainingPlanModel.getUserInTrainingPlan() == null
                    || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
                trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
            }
            trainingPlanModel.setStatus(0);
            trainingPlanModel.setEventPeriodModel(eventPeriodService.findActiveTrainingPeriod().get());

            for (TrainingPlanTakenByUserModel x :trainingPlanModel.getUserInTrainingPlan()){
                x.setStatus("absen");
                x.setTrainingPlan(trainingPlanModel);
            }
            trainingPlanService.addTrainingPlan(trainingPlanModel);

            List<TrainingPlanModel> listTrainingPlan = trainingPlanService.getAllTrainingPlan();
            model.addAttribute("listTrainingPlan", listTrainingPlan);
            redirectAttributes.addFlashAttribute("successtext", "Selamat! Training Plan berhasil ditambahkan");
            return "redirect:/training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa melakukan penambahan karena ini bukan periode training");
            return "redirect:/training-plan";
        }
    }

    @GetMapping("/training-plan/update/{id}")
    public String updateTrainingPlan(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {

        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            model.addAttribute("activeTrainingPeriod",true);

            // method starts here
            TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
            List<UserModel> listUserExisting = userService.getAllUser();
            List<TrainingModel> listTraining = trainingService.getListTraining();
            model.addAttribute("listUserExisting", listUserExisting);
            model.addAttribute("listTraining", listTraining);
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            return "form-update-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa melakukan penambahan karena ini bukan periode training");
            return "redirect:/training-plan/" + id;
        }
    }

    @PostMapping(value = "/training-plan/update", params = { "addRow" })
    public String addRowTrainingPlanUpdate(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model, RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            model.addAttribute("activeTrainingPeriod",true);

            // method starts here
            if (trainingPlanModel.getUserInTrainingPlan() == null
                    || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
                trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
            }
            trainingPlanModel.getUserInTrainingPlan().add(new TrainingPlanTakenByUserModel());
            trainingPlanModel
                    .setIdTraining(trainingService.getTrainingById(trainingPlanModel.getIdTraining().getTrainingId()));
            model.addAttribute("trainingPlanModel", trainingPlanModel);

            List<UserModel> listUserExisting = userService.getAllUser();
            model.addAttribute("listUserExisting", listUserExisting);
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            return "form-update-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah training karena ini bukan periode training");
            return "redirect:/training-plan/" + trainingPlanModel.getIdTraining();
        }


    }

    @PostMapping(value = "/training-plan/update", params = { "deleteRow" })
    private String deleteRowTrainingPlanUpdate(@ModelAttribute TrainingPlanModel trainingPlanModel, RedirectAttributes redirectAttributes,
            @RequestParam("deleteRow") Integer row, Model model) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            model.addAttribute("activeTrainingPeriod",true);

            // method starts here
            final Integer rowId = Integer.valueOf(row);
            TrainingPlanTakenByUserModel trainingPlanTakenByUserModel = trainingPlanModel.getUserInTrainingPlan().remove(rowId.intValue());
            trainingTakenByUserService.deleteTrainingTakenByUserService(trainingPlanTakenByUserModel);

            List<UserModel> listUserExisting = userService.getAllUser();
            trainingPlanModel
                    .setIdTraining(trainingService.getTrainingById(trainingPlanModel.getIdTraining().getTrainingId()));
            model.addAttribute("idTraining", trainingPlanModel.getIdTraining());
            model.addAttribute("listUserExisting", listUserExisting);
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            return "form-update-training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah training karena ini bukan periode training");
            return "redirect:/training-plan/" + trainingPlanModel.getIdTraining();
        }
    }

    @PostMapping(value = "/training-plan/update", params = { "save" })
    public String updateTrainingPlanSubmit(@ModelAttribute TrainingPlanModel trainingPlanModel, Model model,
            RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            // Validasi tanggal di dalam range Event
            if (!trainingPlanService.TrainingDateValidationCheckWithEvent(trainingPlanModel, trainingPlanModel.getEventPeriodModel())){
                redirectAttributes.addFlashAttribute("errortext","Tanggal berlangsung training harus berada di dalam periode event yang berlaku pada training plan ini");
                return "redirect:/training-plan/" + trainingPlanModel.getIdTraining();
            }

            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            redirectAttributes.addFlashAttribute("activeTrainingPeriod",true);

            if (trainingPlanModel.getUserInTrainingPlan() == null
                    || trainingPlanModel.getUserInTrainingPlan().size() == 0) {
                trainingPlanModel.setUserInTrainingPlan(new ArrayList<>());
            }

            trainingPlanModel.setStatus(0);
            trainingPlanModel.setEventPeriodModel(eventPeriodService.findActiveTrainingPeriod().get());
            for (TrainingPlanTakenByUserModel x :trainingPlanModel.getUserInTrainingPlan()){
                x.setStatus("absen");
                x.setTrainingPlan(trainingPlanModel);
            }
            trainingPlanModel.setUserInTrainingPlan(trainingPlanModel.getUserInTrainingPlan());
            trainingPlanService.addTrainingPlan(trainingPlanModel);

            model.addAttribute("listTrainingPlan", trainingPlanModel.getUserInTrainingPlan());
            redirectAttributes.addFlashAttribute("successtext", "Selamat! Training Plan berhasil diubah");
            return "redirect:/training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah training karena ini bukan periode training");
            return "redirect:/training-plan/" + trainingPlanModel.getIdTraining();
        }

    }

    @GetMapping("/training-plan/delete/{id}")
    public String deleteTrainingPlan(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            redirectAttributes.addFlashAttribute("activeTrainingPeriod",true);

            TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
            trainingPlanService.deleteTrainingPlan(trainingPlanModel);
            model.addAttribute("listTrainingPlan", trainingPlanService.getAllTrainingPlan());
            return "redirect:/training-plan";

        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa menghapus training karena ini bukan periode training");
            return "redirect:/training-plan/" + id;
        }

    }

    @PostMapping(value = "/training-plan/{id}", params = { "confirm" })
    public String confirmTrainingPlan(@PathVariable Long id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (eventPeriodService.findActiveTrainingPeriod().isPresent()) {
            model.addAttribute("eventPeriod", eventPeriodService.findActiveTrainingPeriod().get());
            redirectAttributes.addFlashAttribute("activeTrainingPeriod",true);

            TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
            if (trainingPlanModel.getStatus() == 2){
                // Current status = active
                // want to change from active ==> done
                if (LocalDate.now().isBefore(trainingPlanModel.getDateEnd())) {
                    redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah status training menjadi \"done\" karena belum mencapai tanggal berakhir training");
                    return "redirect:/training-plan/" + id;
                } else {
                    trainingPlanModel.setStatus(3);
                }
            }
            else if (trainingPlanModel.getStatus() ==1){
                // Current status = confirmed
                // want to change from confirmed ==> active
                if (LocalDate.now().isBefore(trainingPlanModel.getDateStart())) {
                    redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah status training menjadi \"active\" karena belum mencapai tanggal mulai training");
                    return "redirect:/training-plan/" + id;
                } else {
                    trainingPlanModel.setStatus(2);
                }
            }
            else {
                // Current status: in review, or done
                trainingPlanModel.setStatus(Math.min(trainingPlanModel.getStatus()+1,3));
            }

            trainingPlanService.addTrainingPlan(trainingPlanModel);
            redirectAttributes.addFlashAttribute("successtext", "Status Training berhasil diubah!");
            model.addAttribute("trainingPlanModel", trainingPlanModel);
            return "redirect:/training-plan/" + id;


        } else {
            redirectAttributes.addFlashAttribute("errortext","Tidak bisa mengubah status training ini karena ini bukan periode training");
            return "redirect:/training-plan/" + id;
        }
    }

    @GetMapping("/training-plan/{id}/absensi")
    public String formAbsen(@PathVariable Long id, Model model) {
        TrainingPlanModel trainingPlanModel = trainingPlanService.getTrainingPlanById(id);
//        List<TrainingPlanTakenByUserModel> listTrainingTaken = trainingTakenByUserService.getTrainingTakenByTrainingPlan(trainingPlanModel);
        model.addAttribute("trainingPlan", trainingPlanModel);
        model.addAttribute("listTrainingTaken", trainingPlanModel.getUserInTrainingPlan());

        return "form-absen-training";
    }

    @PostMapping(value = "/training-plan/{id}/absensi", params = "save")
    public String confirmAbsen(@PathVariable Long id, Model model, @ModelAttribute TrainingPlanModel trainingPlan) {
        List<TrainingPlanTakenByUserModel> listUser = trainingPlan.getUserInTrainingPlan();

        TrainingPlanModel trainingPlanOriginal = trainingPlanService.getTrainingPlanById(id);
        BeanUtils.copyProperties(trainingPlanOriginal,trainingPlan);

        trainingPlan.setUserInTrainingPlan(listUser);

        for (TrainingPlanTakenByUserModel x :trainingPlan.getUserInTrainingPlan()){
            x.setTrainingPlan(trainingPlan);
        }
        trainingPlanService.addTrainingPlan(trainingPlan);

        return "redirect:/training-plan/" + trainingPlan.getIdTrainingPlan();
    }
}
