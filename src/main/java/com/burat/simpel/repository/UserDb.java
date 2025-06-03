package com.burat.simpel.repository;

import com.burat.simpel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
    UserModel findByUsername(String username);
    @Query("SELECT DISTINCT a.username FROM UserModel a")
    List<String> findUsernameUser();


    @Query(nativeQuery = true, value = "select * from user where account_uuid not in (select a.user_uuid from assessment a where assessor_uuid=:assessorUuid and id_event_period=:eventId);")
    List<UserModel> findUserNotAssessedByAssessorIn(@Param("assessorUuid") String assessorUuid, @Param("eventId") Long eventId);

    @Query(nativeQuery = true, value = "select * from user where account_uuid in (select a.user_uuid from assessment a where assessor_uuid=:assessorUuid and id_event_period=:eventId);")
    List<UserModel> findUserAlreadyAssessedByAssessorInEvent(@Param("assessorUuid") String assessorUuid, @Param("eventId") Long eventId);
}
