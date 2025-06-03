package com.burat.simpel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name="account")
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
//@DiscriminatorColumn(name="role",
//        discriminatorType = DiscriminatorType.STRING)
public class AccountModel {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "account_uuid")
    private String accountUuid;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_depan", nullable = false)
    private String namaDepan;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_belakang", nullable = false)
    private String namaBelakang;

    @NotNull
    @Column(name = "jenis_kelamin", nullable = false)
    private Boolean jenisKelamin;

    @NotNull
    @Column(name = "tanggal_lahir",nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalLahir;

    @NotNull
    @Size(max = 100)
    @Column(name = "email",nullable = false)
    private String email;

    @NotNull
    @Column(name = "no_telepon",nullable = false)
    private Long noTelepon;

    @NotNull
    @Size(max = 100)
    @Column(name = "role",nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_divisi", referencedColumnName = "id_divisi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DivisiModel divisiModel;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_title", referencedColumnName = "id_title", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TitleModel titleModel;


}
