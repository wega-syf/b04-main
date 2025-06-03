package com.burat.simpel.repository;

import com.burat.simpel.model.EventPeriodModel;
import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventPeriodDb extends JpaRepository<EventPeriodModel,Long> {
    EventPeriodModel findByPeriodName(String name);
    List<EventPeriodModel> findAllByJenis(String jenis);

    EventPeriodModel findFirstByIsActiveIsTrue();

    List<EventPeriodModel> findAllByIsActiveIsFalse();

    EventPeriodModel findByIdEventPriod(Long idEventPriod);
    @Modifying
    @Transactional
    @Query("UPDATE EventPeriodModel SET isActive = 0")
    void disableAllPeriod();

    @Query(nativeQuery = true, value = "select * from event_period where date_start <= (:dateEndInput) and date_end >= (:dateStartInput)")
    Optional<EventPeriodModel> findOverlapTime(@Param("dateStartInput") String dateStartInput, @Param("dateEndInput") String dateEndInput);

    @Query(nativeQuery = true, value = "select * from event_period where date_start = :now")
    Optional<EventPeriodModel> findEventPeriodStartsToday(LocalDate now);

    @Query(nativeQuery = true, value = "select * from event_period where jenis = 'training' and is_active = 1 limit 1;")
    Optional<EventPeriodModel> findEventPeriodTrainingActive();

    @Query(nativeQuery = true, value = "select * from event_period where jenis = 'assessment' and is_active = 1 limit 1;")
    Optional<EventPeriodModel> findEventPeriodAssessmentActive();
}
