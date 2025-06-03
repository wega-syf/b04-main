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
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competency_level")
public class CompetencyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_level")
    private Long idLevel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_comp", referencedColumnName = "id_comp", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Competency idCompetency;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @NotNull
    @Size(max = 5000)
    @Column(name = "deskripsi", nullable = false)
    private String deskripsi;

    @ManyToMany(mappedBy = "levelOfTraining")
    private List<TrainingModel> training;

    @OneToMany(mappedBy = "competencyLevel", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<AssessmentLevelModel> listAssessment;

}
