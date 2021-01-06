package io.rk.nac.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table
@Entity
@Data
public class Price {
    @Id
    private int id;
    private String firmName;
    private double marketPrice;
    private String onDate;
}
