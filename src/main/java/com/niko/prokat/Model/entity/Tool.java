package com.niko.prokat.Model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "tools")
@Entity
public class Tool extends AbstractEntity {
    private String image;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Brand brand;
    private String power;
    private Double weight;
    private Integer price;
    private Integer pledge;
}


