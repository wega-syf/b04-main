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
@Table(name = "title")
public class TitleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_title")
    private Long idTitle;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 500)
    @Column(name = "deskripsi")
    private String deskripsi;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_divisi", referencedColumnName = "id_divisi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DivisiModel divisi;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "title")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CompetencyModel> listCompetencyModel;
}
