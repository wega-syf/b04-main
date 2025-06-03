package com.burat.simpel.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="assessor")
//@DiscriminatorValue("Assessor")
public class AssessorModel extends AccountModel{
    @OneToMany(mappedBy = "assessor", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<AssessmentModel> listAssessment;
}
