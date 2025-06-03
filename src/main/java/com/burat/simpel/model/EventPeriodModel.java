package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Table(name = "event_period")
public class EventPeriodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_event_period")
    private Long idEventPriod;

    @NotNull
    @Size(max = 100)
    @Column(name = "period_name", nullable = false, unique = true)
    private String periodName;

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
    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<AssessmentModel> listAssessment;

    @NotNull
    @Size(max = 100)
    @Column(name = "jenis", nullable = false)
    private String jenis;
}
