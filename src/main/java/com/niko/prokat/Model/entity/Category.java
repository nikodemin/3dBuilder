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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Category> children;
}
