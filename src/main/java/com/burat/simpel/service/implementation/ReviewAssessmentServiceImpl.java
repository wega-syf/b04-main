package com.burat.simpel.service.implementation;

import com.burat.simpel.model.*;
import com.burat.simpel.repository.*;
import com.burat.simpel.service.ReviewAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewAssessmentServiceImpl implements ReviewAssessmentService {

    @Autowired
    private ReviewAssessmentDb reviewAssessmentDb;

    @Autowired
    private AssessmentDb assessmentDb;

    @Autowired
    private AssessorDb assessorDb;

    @Autowired
    private UserDb userDb;

    @Autowired
    private EventPeriodDb eventPeriodDb;

    @Override
    public List<EventPeriodModel> getAllEventPeriodByJenis(String jenis) {
        return eventPeriodDb.findAllByJenis(jenis);
    }

    public List<ReviewAssessmentModel> getReviewSelf(EventPeriodModel eventPeriod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        UserModel user = userDb.findByUsername(currentPrincipalName);
        List<ReviewAssessmentModel> listReview = reviewAssessmentDb.findReviewByUsername(user, eventPeriod);

        return listReview;
    }

    public List<ReviewAssessmentModel> getReviewByUsername(String username, EventPeriodModel eventPeriod) {
        UserModel user = userDb.findByUsername(username);
        List<ReviewAssessmentModel> listReview = reviewAssessmentDb.findReviewByUsername(user, eventPeriod);

        return listReview;
    }

    public List<UserModel> getListUser(Long idEventPriod) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalRole = authentication.getAuthorities().toString();
        List<UserModel> listUser = new ArrayList<>();

        EventPeriodModel eventPeriod = eventPeriodDb.findByIdEventPriod(idEventPriod);

        if (currentPrincipalRole.equals("[admin]")){
            listUser = assessmentDb.findUserAdmin(eventPeriod);
        } else if (currentPrincipalRole.equals("[assessor]")) {
            AssessorModel assessor = assessorDb.findByUsername(authentication.getName());
            listUser = assessmentDb.findUserAssessor(assessor, eventPeriod);
        }

        return listUser;
    }

    public EventPeriodModel getPeriod(Long idEventPriod) {
        EventPeriodModel eventPeriod = eventPeriodDb.findByIdEventPriod(idEventPriod);
        return eventPeriod;
    }

    public void setReview(AssessmentModel assessment) {
        List<ReviewAssessmentModel> listReview = reviewAssessmentDb.findReviewByUsername(assessment.getUser(), assessment.getEvent());

        if (listReview.isEmpty()) {
            for (AssessmentLevelModel a : assessment.getListAssessment()) {
                ReviewAssessmentModel review = new ReviewAssessmentModel();
                review.setUser(assessment.getUser());
                review.setEvent(assessment.getEvent());
                review.setCompetencyLevel(a.getCompetencyLevel());
                review.setRerataGap(a.getGap());
                review.setCount(1L);
                review.setTotal(a.getGap());
                reviewAssessmentDb.save(review);
            }
        } else {
            for (AssessmentLevelModel a : assessment.getListAssessment()) {
                ReviewAssessmentModel review = reviewAssessmentDb.findReviewByUsernameComp(assessment.getUser(), a.getCompetencyLevel(), assessment.getEvent()).get(0);
                review.setCount(review.getCount()+1);
                review.setTotal(review.getTotal() + a.getGap());
                review.setRerataGap(Math.floorDiv(review.getTotal(), review.getCount()));
                reviewAssessmentDb.save(review);
            }
        }
    }
}
