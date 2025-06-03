package com.burat.simpel.repository;

import com.burat.simpel.dto.AllReportDTO;
import com.burat.simpel.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.burat.simpel.dto.TrainingRecDTO;


import java.util.List;


public interface ReviewAssessmentDb extends JpaRepository<ReviewAssessmentModel,Long> {
    @Query("SELECT a FROM ReviewAssessmentModel a WHERE a.user = :user and a.event = :event")
    List<ReviewAssessmentModel> findReviewByUsername(@Param("user") UserModel user,
                                                     @Param("event") EventPeriodModel event);

    @Query("SELECT a FROM ReviewAssessmentModel a WHERE a.user = :user and a.competencyLevel = :competencyLevel and a.event = :event")
    List<ReviewAssessmentModel> findReviewByUsernameComp(@Param("user") UserModel user,
                                                         @Param("competencyLevel") CompetencyLevel competencyLevel,
                                                         @Param("event") EventPeriodModel event);

    @Query("SELECT new com.burat.simpel.dto.TrainingRecDTO(r.competencyLevel, count(r.competencyLevel)) FROM ReviewAssessmentModel r WHERE r.event = :eventPeriod and r.rerataGap < 0 GROUP BY r.competencyLevel order by count(r.competencyLevel) desc")
    List<TrainingRecDTO> getCompetencyCounts(@Param("eventPeriod") EventPeriodModel eventPeriod);

    List<ReviewAssessmentModel> findAllByCompetencyLevel_IdLevelAndEventAndRerataGapLessThan(Long idLevel, EventPeriodModel eventPeriod, Long zero);

    @Query("SELECT new com.burat.simpel.dto.AllReportDTO(r.event, AVG(CASE WHEN r.rerataGap <= 0 THEN r.rerataGap ELSE 0 END), COUNT(DISTINCT r.user)) FROM ReviewAssessmentModel r GROUP BY r.event order by r.event.dateStart asc")
    List<AllReportDTO> getAllReports();

    @Query("SELECT new com.burat.simpel.dto.AllReportDTO(r.event, AVG(CASE WHEN r.rerataGap <= 0 THEN r.rerataGap ELSE 0 END), COUNT(DISTINCT r.user)) FROM DivisiModel d, ReviewAssessmentModel r WHERE r.user.divisiModel = :divisi group by r.event order by r.event.dateStart asc")
    List<AllReportDTO> getDivisionalReports(@Param("divisi") DivisiModel divisi);

    @Query("SELECT new com.burat.simpel.dto.AllReportDTO(a.assessment.event, AVG(CASE WHEN a.gap <= 0 THEN a.gap ELSE 0 END), COUNT(DISTINCT a.assessment.user)) FROM AssessmentLevelModel a WHERE a.competencyLevel.idCompetency = :competency group by a.assessment.event order by a.assessment.event.dateStart asc")
    List<AllReportDTO> getCompetencyReports(@Param("competency") Competency competency);
}
