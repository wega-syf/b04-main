package com.burat.simpel.dto;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.TrainingModel;

import java.util.ArrayList;
import java.util.List;

public class TrainingRecDTO {
    private CompetencyLevel competencyLevel;
    private Long jumlahUser;
    private List<TrainingModel> listRekomendasiTraining = new ArrayList<>();

    public TrainingRecDTO(CompetencyLevel competencyLevel, Long jumlahUser) {
        this.competencyLevel = competencyLevel;
        this.jumlahUser = jumlahUser;
    }

    public CompetencyLevel getCompetencyLevel() {
        return competencyLevel;
    }

    public Long getJumlahUser() {
        return jumlahUser;
    }

    public List<TrainingModel> getListRekomendasiTraining() {
        return listRekomendasiTraining;
    }

    public void setCompetencyLevel(CompetencyLevel competencyLevel) {
        this.competencyLevel = competencyLevel;
    }

    public void setJumlahUser(Long jumlahUser) {
        this.jumlahUser = jumlahUser;
    }

    public void setListRekomendasiTraining(List<TrainingModel> listRekomendasiTraining) {
        this.listRekomendasiTraining = listRekomendasiTraining;
    }
}
