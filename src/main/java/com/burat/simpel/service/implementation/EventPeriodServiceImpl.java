package com.burat.simpel.service.implementation;

import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.repository.EventPeriodDb;
import com.burat.simpel.service.AccountService;
import com.burat.simpel.service.EventPeriodService;
import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EventPeriodServiceImpl implements EventPeriodService {
    @Autowired
    EventPeriodDb eventPeriodDb;

    @Autowired
    AccountService accountService;

    @Override
    public EventPeriodModel getByEventPeriodName(String name) {
        return eventPeriodDb.findByPeriodName(name);
    }

    @Override
    public List<EventPeriodModel> getAllEventPeriod() {
        return eventPeriodDb.findAll();
    }

    @Override
    public List<EventPeriodModel> getEventPeriodByJenis(String jenis) {
        return eventPeriodDb.findAllByJenis(jenis);
    }

    @Override
    public Optional<EventPeriodModel> getEventPeriodById(Long id) {
        return eventPeriodDb.findById(id);
    }

    @Override
    public List<EventPeriodModel> getAllEventPeriodNotActive() {
        return eventPeriodDb.findAllByIsActiveIsFalse();
    }

    @Override
    public void addEventPeriodModel(EventPeriodModel eventPeriodModel) {
        eventPeriodDb.save(eventPeriodModel);
    }

    @Override
    public EventPeriodModel findActivePeriod() {
        return eventPeriodDb.findFirstByIsActiveIsTrue();
    }

    @Override
    public void inActivateAllEventPeriod() {
        eventPeriodDb.disableAllPeriod();
    }

    @Override
    public void activateEventManual(Long id) {
        EventPeriodModel model = eventPeriodDb.findByIdEventPriod(id);
        EventPeriodModel alreadyActive = findActivePeriod();
        alreadyActive.setIsActive(false);
        eventPeriodDb.save(alreadyActive);
        model.setIsActive(true);
        eventPeriodDb.save(model);

        accountService.logOutAllAccounts();
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT+7:00")
    public void activateEventAutomaticScheduled(){
        System.out.println("Automatic Event Period activation process for today, " +LocalDate.now()+ " has started");

        // Find one that has the same date as today
        Optional<EventPeriodModel> eventPeriodModelOptional = eventPeriodDb.findEventPeriodStartsToday(LocalDate.now());
        if (eventPeriodModelOptional.isPresent()){
            System.out.println("A new event is activated. '"+eventPeriodModelOptional.get().getPeriodName() +"' is now active");
            eventPeriodDb.disableAllPeriod();
            // Setting the new event active
            eventPeriodModelOptional.get().setIsActive(true);
            eventPeriodDb.save(eventPeriodModelOptional.get());
            System.out.println("Other event is now set as Inactive, please login as Admin to manage event on the Time Management page");
        } else {
            System.out.println("No new event is activated");
        }
    }

    @Override
    public Optional<EventPeriodModel> findActiveTrainingPeriod() {
        return eventPeriodDb.findEventPeriodTrainingActive();
    }

    @Override
    public Optional<EventPeriodModel> findActiveAssessmentPeriod() {
        return eventPeriodDb.findEventPeriodAssessmentActive();
    }

    @Override
    public Optional<EventPeriodModel> findOverlapDate(EventPeriodModel eventPeriodModel) {
        DateTimeFormatter targetFormat  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateStart = targetFormat.format(eventPeriodModel.getDateStart());
        String formattedDateEnd = targetFormat.format(eventPeriodModel.getDateEnd());
        return eventPeriodDb.findOverlapTime(formattedDateStart, formattedDateEnd);
    }




}
