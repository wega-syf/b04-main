package com.burat.simpel.service;

import com.burat.simpel.model.AssessmentModel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;
import com.burat.simpel.model.UserModel;

import java.util.List;

public interface ReviewAssessmentService {
    List<ReviewAssessmentModel> getReviewSelf(EventPeriodModel eventPeriod);

    List<ReviewAssessmentModel> getReviewByUsername(String username, EventPeriodModel eventPeriod);

    List<UserModel> getListUser(Long idEventPriod);
    List<EventPeriodModel> getAllEventPeriodByJenis(String jenis);
    EventPeriodModel getPeriod(Long idEventPriod);
    void setReview(AssessmentModel assessment);
}
