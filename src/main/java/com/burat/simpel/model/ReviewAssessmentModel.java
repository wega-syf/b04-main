package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review_assessment")
public class ReviewAssessmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review_assessment")
    private Long idReviewAssessment;

    @NotNull
    @Column(name = "rerata_gap")
    private Long rerataGap;

    @NotNull
    @Column(name = "count")
    private Long count;

    @NotNull
    @Column(name = "total")
    private Long total;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_event_period", referencedColumnName = "id_event_period", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventPeriodModel event;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_uuid", referencedColumnName = "account_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_comp_level", referencedColumnName = "id_level")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CompetencyLevel competencyLevel;
}
