package com.burat.simpel.controller;

import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;
import com.burat.simpel.model.UserModel;
import com.burat.simpel.service.CompetencyService;
import com.burat.simpel.service.ReviewAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReviewAssessmentController {
    @Autowired
    ReviewAssessmentService reviewAssessmentService;

    @GetMapping("/review")
    private String reviewPeriode(Model model) {
        List<EventPeriodModel> listEventPeriod = reviewAssessmentService.getAllEventPeriodByJenis("assessment");
        model.addAttribute("listEventPeriod", listEventPeriod);
        return "periode-review";
    }

    @GetMapping(value="/review/me")
    private String viewReview(@RequestParam(value = "idEventPriod") Long idEventPriod, Model model) {
        EventPeriodModel eventPeriod = reviewAssessmentService.getPeriod(idEventPriod);
        List<ReviewAssessmentModel> listReview = reviewAssessmentService.getReviewSelf(eventPeriod);

        model.addAttribute("listReview", listReview);
        return "view-review";
    }

    @GetMapping("/review/user/{username}")
    private String viewReviewUser(@PathVariable(value = "username") String username, @RequestParam(value = "idEventPriod") Long idEventPriod, Model model) {
        EventPeriodModel eventPeriod = reviewAssessmentService.getPeriod(idEventPriod);
        List<ReviewAssessmentModel> listReview = reviewAssessmentService.getReviewByUsername(username, eventPeriod);

        model.addAttribute("listReview", listReview);
        model.addAttribute("username", username);
        model.addAttribute("eventPeriod", eventPeriod);
        return "view-review-user";
    }

    @GetMapping("/review/list-user")
    private String viewListUser(@RequestParam(value = "idEventPriod") Long idEventPriod, Model model) {
        List<UserModel> listUser = reviewAssessmentService.getListUser(idEventPriod);
        EventPeriodModel eventPeriod = reviewAssessmentService.getPeriod(idEventPriod);

        model.addAttribute("listUser", listUser);
        model.addAttribute("eventPeriod", eventPeriod);
        return "list-user-review";
    }
}
