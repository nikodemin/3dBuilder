package com.niko.prokat.Model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "brands")
public class Brand extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private String site;
}
