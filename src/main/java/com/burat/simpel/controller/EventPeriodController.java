package com.burat.simpel.controller;

import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.service.EventPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EventPeriodController {

    @Autowired
    EventPeriodService eventPeriodService;

    final String BASE_URL = "/time-management";

    @GetMapping(BASE_URL)
    private String getAllTimeManagement(Model model){
        List<EventPeriodModel> eventPeriodModelList = eventPeriodService.getAllEventPeriodNotActive();
        EventPeriodModel eventPeriodModelActive = eventPeriodService.findActivePeriod();
        model.addAttribute("eventPeriodList", eventPeriodModelList);
        model.addAttribute("eventActive", eventPeriodModelActive);
        return "time-management-viewall";
    }

    @GetMapping(BASE_URL+"/add")
    private String viewAddTimeManagementForm(Model model){
        EventPeriodModel eventPeriodModelNew = new EventPeriodModel();
        model.addAttribute("eventPeriodModel", eventPeriodModelNew);
        return "time-management-addform";
    }
    @PostMapping(BASE_URL+"/add")
    private String submitAddTimeManagementForm(Model model, RedirectAttributes redirectAttributes, @ModelAttribute EventPeriodModel eventPeriodModel){
        eventPeriodModel.setIsActive(false);

        // Validasi form
        if (eventPeriodModel.getDateEnd().isBefore(eventPeriodModel.getDateStart())) {
            redirectAttributes.addFlashAttribute("errortext","Tanggal berakhir tidak boleh sebelum tanggal mulai");
            return "redirect:"+BASE_URL+"/add";
        } else if (eventPeriodService.findOverlapDate(eventPeriodModel).isPresent()) {
            redirectAttributes.addFlashAttribute("errortext","Tanggal berlangsung period bertabrakan dengan period lain");
            return "redirect:"+BASE_URL+"/add";
        }else if (eventPeriodService.getByEventPeriodName(eventPeriodModel.getPeriodName()) != null ) {
            redirectAttributes.addFlashAttribute("errortext", "Terdapat duplikat nama event period, ubah ke nama yang lain");
            return "redirect:" + BASE_URL + "/add";
        } else {
            eventPeriodService.addEventPeriodModel(eventPeriodModel);
            redirectAttributes.addFlashAttribute("successtext","Berhasil menambahkan event baru '" + eventPeriodModel.getPeriodName() +"' dengan jenis " +eventPeriodModel.getJenis());
            return "redirect:"+BASE_URL;
        }
    }

    @GetMapping(BASE_URL+"/activate/{id}")
    private String activateEvent(Model model, RedirectAttributes redirectAttributes, @PathVariable Long id){
        redirectAttributes.addFlashAttribute("successtext","Berhasil mengaktifkan event");
        eventPeriodService.activateEventManual(id);

        return "redirect:/logout";
    }



}
