package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "training_plan")
public class TrainingPlanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training_plan")
    private Long idTrainingPlan;

    @NotNull
    @Column(name = "nama")
    @Size(max=100)
    private String nama;

    @NotNull
    @Column(name = "date_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    @NotNull
    @Column(name = "date_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    @Size(max = 1000)
    @Column(name = "deskripsi")
    private String deskripsi;

    @NotNull
    @Column(name = "budget")
    private Long budget;

    @NotNull
    @Column(name = "status")
    // In review
    // Confirmed
    // Active
    // Done
    private int status;


//    @ManyToMany
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinTable(
//            name = "training_plan_user",
//            joinColumns = @JoinColumn(name = "id_training_plan"),
//            inverseJoinColumns = @JoinColumn(name = "account_uuid"),
//            foreignKey = @ForeignKey(name = "FK_training_plan_training",
//                    foreignKeyDefinition = "FOREIGN KEY (id_training_plan) REFERENCES training_plan(id_training_plan) ON DELETE CASCADE"),
//            inverseForeignKey = @ForeignKey(name = "FK_training_plan_user",
//                    foreignKeyDefinition = "FOREIGN KEY (account_uuid) REFERENCES user(account_uuid) ON DELETE CASCADE"),
//            uniqueConstraints = @UniqueConstraint(columnNames = {"id_training_plan", "account_uuid"})
//    )
//    List<UserModel> userInTrainingPlan;
    @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<TrainingPlanTakenByUserModel> userInTrainingPlan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_training", referencedColumnName = "id_training", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrainingModel idTraining;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_event", referencedColumnName = "id_event_period", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventPeriodModel eventPeriodModel;
}
