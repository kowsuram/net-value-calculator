package io.rk.nac.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table
@Entity
@Data
public class Holding {
    @Id
    private int id;
    private String firmName;
    private String portfolio;
    private String onDate;
    private int quantity;
}
