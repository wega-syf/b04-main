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
@Table(name = "divisi")
@AllArgsConstructor
@NoArgsConstructor
public class DivisiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_divisi")
    private Long idDivisi;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false, unique = true)
    private String nama;

    @NotNull
    @Size(max = 500)
    @Column(name = "deskripsi", nullable = true)
    private String deskripsi;

    @OneToMany(mappedBy = "divisi", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<TitleModel> listTitle;
}
