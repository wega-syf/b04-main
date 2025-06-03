package com.burat.simpel.service;

import com.burat.simpel.dto.TrainingRecDTO;
import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;

import java.util.List;

public interface TrainingRecService {
    public List<ReviewAssessmentModel> getAllReviewAssessmentByCompLevel(Long idLevel, Long idEventPriod);
    public List<TrainingRecDTO> getAllTrainingRecDTO(Long idEventPriod);

    public List<EventPeriodModel> getAllEventPeriodByJenis(String jenis);
}
