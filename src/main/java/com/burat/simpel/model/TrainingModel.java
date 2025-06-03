package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "training")
public class TrainingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training")
    private Long trainingId;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false, unique = true)
    private String nama;

    @Size(max = 1000)
    @Column(name = "deskripsi", nullable = true)
    private String deskripsi;

    @NotNull
    @Size(max = 255)
    @Column(name = "penyedia", nullable = false)
    private String penyedia;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "training_level",
            joinColumns = @JoinColumn(name = "id_training"),
            inverseJoinColumns = @JoinColumn(name = "id_level"),
            foreignKey = @ForeignKey(name = "FK_training_level_training",
                    foreignKeyDefinition = "FOREIGN KEY (id_training) REFERENCES training(id_training) ON DELETE CASCADE"),
            inverseForeignKey = @ForeignKey(name = "FK_training_level_level",
                    foreignKeyDefinition = "FOREIGN KEY (id_level) REFERENCES competency_level(id_level) ON DELETE CASCADE"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_training", "id_level"})
    )
    List<CompetencyLevel> levelOfTraining;
}
