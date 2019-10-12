package com.niko.prokat.Model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String name;
    private String image;
    private String description;
    private Boolean isRoot = false;

    @OneToMany(fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Category> children;
}
