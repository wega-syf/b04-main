package com.burat.simpel.service;

import com.burat.simpel.model.EventPeriodModel;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

public interface EventPeriodService {
    List<EventPeriodModel> getAllEventPeriod();
    List<EventPeriodModel> getAllEventPeriodNotActive();
    void addEventPeriodModel(EventPeriodModel eventPeriodModel);
    EventPeriodModel getByEventPeriodName(String name);

    void inActivateAllEventPeriod();

    EventPeriodModel findActivePeriod();

    Optional<EventPeriodModel> getEventPeriodById(Long id);

    void activateEventManual(Long id);

    void activateEventAutomaticScheduled();

    Optional<EventPeriodModel> findActiveTrainingPeriod();

    Optional<EventPeriodModel> findActiveAssessmentPeriod();

    Optional<EventPeriodModel> findOverlapDate(EventPeriodModel eventPeriodModel);

    List<EventPeriodModel> getEventPeriodByJenis(String jenis);

}
