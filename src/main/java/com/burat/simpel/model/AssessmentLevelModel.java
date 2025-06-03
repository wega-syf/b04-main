package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assessment_level")
public class AssessmentLevelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assessment_level")
    private Long idAssessmentLevel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_assess", referencedColumnName = "id_assess", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AssessmentModel assessment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_comp_level", referencedColumnName = "id_level", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CompetencyLevel competencyLevel;

    @Column(nullable = false, name = "result")
    private Long result;

    @Column(nullable = false, name = "gap")
    private Long gap;


}
