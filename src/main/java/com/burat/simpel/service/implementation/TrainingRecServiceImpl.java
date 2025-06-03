package com.burat.simpel.service.implementation;

import com.burat.simpel.dto.TrainingRecDTO;
import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.repository.EventPeriodDb;
import com.burat.simpel.repository.ReviewAssessmentDb;
import com.burat.simpel.repository.TrainingDb;
import com.burat.simpel.service.TrainingRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingRecServiceImpl implements TrainingRecService {

    @Autowired
    ReviewAssessmentDb reviewAssessmentDb;

    @Autowired
    TrainingDb trainingDb;

    @Autowired
    EventPeriodDb eventPeriodDb;

    @Override
    public List<ReviewAssessmentModel> getAllReviewAssessmentByCompLevel(Long idLevel, Long idEventPriod) {
        EventPeriodModel eventPeriod = eventPeriodDb.findByIdEventPriod(idEventPriod);
        return reviewAssessmentDb.findAllByCompetencyLevel_IdLevelAndEventAndRerataGapLessThan(idLevel, eventPeriod, 0L);
    }

    @Override
    public List<TrainingRecDTO> getAllTrainingRecDTO(Long idEventPriod) {
        EventPeriodModel eventPeriod = eventPeriodDb.findByIdEventPriod(idEventPriod);
        List<TrainingRecDTO> listTrainingRec = reviewAssessmentDb.getCompetencyCounts(eventPeriod);
        List<TrainingModel> listTraining = trainingDb.findAll();

        for (TrainingRecDTO tr : listTrainingRec) {
            CompetencyLevel cl = tr.getCompetencyLevel();
            List<TrainingModel> listRec = new ArrayList<>();

            for (TrainingModel t : listTraining) {
                for (CompetencyLevel clt : t.getLevelOfTraining()) {
                    if (cl == clt) {
                        listRec.add(t);
                        break;
                    }
                }
            }

            tr.setListRekomendasiTraining(listRec);
        }

        return listTrainingRec;
    }

    @Override
    public List<EventPeriodModel> getAllEventPeriodByJenis(String jenis) {
        return eventPeriodDb.findAllByJenis(jenis);
    }


}
