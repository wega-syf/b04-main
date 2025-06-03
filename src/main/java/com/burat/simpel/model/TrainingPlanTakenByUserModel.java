package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "training_plan_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_uuid","id_training_plan"})})
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanTakenByUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrainingPlanTakenUser;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_uuid", referencedColumnName = "account_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel userModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_training_plan", referencedColumnName = "id_training_plan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrainingPlanModel trainingPlan;

    @NotNull
    @Column(name = "status", nullable = false)
    @Size(max = 100)
    // Hadir
    // Izin
    // Absen
    private String status;

}