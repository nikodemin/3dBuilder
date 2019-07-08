package com.dBuider.app.Model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "brands")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Brand
{
    @Column(unique = true, nullable = false)
    private final String name;
    private final String site;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
