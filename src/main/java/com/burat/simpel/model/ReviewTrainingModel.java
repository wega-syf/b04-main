package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "review_training")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTrainingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review_training")
    private Long idReviewTraining;

    @NotNull
    @Column(name = "date_written")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateWritten;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_uuid", referencedColumnName = "account_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_training_plan", referencedColumnName = "id_training_plan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrainingPlanModel idTrainPlan;

    @NotNull
    @Column(name = "rating_average")
    private Float ratingAverage;

    @NotNull
    @Column(name = "esai_feedback")
    @Size(max = 5000)
    private String esaiFeedback;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q1;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q2;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q3;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q4;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q5;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q6;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q7;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q8;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q9;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer q10;


}
