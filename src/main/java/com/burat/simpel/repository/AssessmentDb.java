package com.burat.simpel.repository;

import com.burat.simpel.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentDb extends JpaRepository<AssessmentModel,Long> {

    @Query("SELECT DISTINCT a.user FROM AssessmentModel a WHERE a.event = :eventPeriod")
    List<UserModel> findUserAdmin(@Param("eventPeriod") EventPeriodModel eventPeriod);

    @Query("SELECT DISTINCT a.user FROM AssessmentModel a WHERE a.assessor = :assessor and a.event = :eventPeriod")
    List<UserModel> findUserAssessor(@Param("assessor") AssessorModel assessor,
                                     @Param("eventPeriod") EventPeriodModel eventPeriod);

    @Query("SELECT DISTINCT a.user FROM AssessmentModel a")
    List<UserModel> findUserAdmin();

    @Query("SELECT DISTINCT a.user FROM AssessmentModel a WHERE a.assessor = :assessor")
    List<UserModel> findUserAssessor(@Param("assessor") AssessorModel assessor);

    @Query(nativeQuery = true, value = "select * from assessment where assessor_uuid=(:assessorUuid) and user_uuid=(:userUuid) and id_event_period = (:id_event);")
    Optional<AssessmentModel> checkDuplicateByUserAssessorEvent(@Param("assessorUuid") String assessorUuid, @Param("userUuid") String userUuid, @Param("id_event") Long id_event);
    @Query(nativeQuery = true, value = "select * from assessment where id_event_period = (:eventId);")
    List<AssessmentModel> findAllByEventPeriodId(@Param("eventId") Long eventId);

    @Query(nativeQuery = true, value = "select * from assessment where assessor_uuid=(:assessorUuid) and id_event_period = (:eventId);")
    List<AssessmentModel> findAllByEventPeriodAndAssessor(@Param("eventId") Long eventId, @Param("assessorUuid") String assessorUuid);

    @Query(nativeQuery = true, value = "select * from assessment where assessor_uuid=(:assessorUuid)")
    List<AssessmentModel> findAllByAssessor(@Param("assessorUuid") String assessorUuid);

}
