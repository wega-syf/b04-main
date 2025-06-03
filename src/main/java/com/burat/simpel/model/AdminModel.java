package com.burat.simpel.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name="admin")
//@DiscriminatorValue("Admin")
public class AdminModel extends AccountModel{
}
