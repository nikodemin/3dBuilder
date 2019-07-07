package com.dBuider.app.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "tools")
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Tool
{
    private final String image;
    private final String category;
    private final String subcat;

    private final String name;
    private final String description;
    @OneToOne
    private final Brand brand;
    private final String power;
    private final Double weight;
    private final Integer price;
    private final Integer pledge;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}


