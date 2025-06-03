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
@Table(name = "competency_model")
public class CompetencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_model")
    private Long idModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_title", referencedColumnName = "id_title", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TitleModel title;

    @Size(max = 500)
    @Column(name = "deskripsi", nullable = true)
    private String deskripsi;


    // @OneToMany(mappedBy = "competencyModel", fetch = FetchType.LAZY)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // List<CompetencyLevel> listCompetencyLevel;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "title_comp",
            joinColumns = @JoinColumn(name = "id_model"),
            inverseJoinColumns = @JoinColumn(name = "id_level"),
            foreignKey = @ForeignKey(name = "FK_title_comp_competency_model",
                    foreignKeyDefinition = "FOREIGN KEY (id_model) REFERENCES competency_model(id_model) ON DELETE CASCADE"),
            inverseForeignKey = @ForeignKey(name = "FK_title_comp_competency_model",
                    foreignKeyDefinition = "FOREIGN KEY (id_level) REFERENCES competency_level(id_level) ON DELETE CASCADE"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_model", "id_level"}))
    List<CompetencyLevel> listCompetencyLevel;
}
