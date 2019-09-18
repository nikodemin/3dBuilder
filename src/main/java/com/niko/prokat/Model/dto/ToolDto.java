package com.niko.prokat.Model.dto;

import lombok.Data;

@Data
public class ToolDto {
    private Long id;
    private String name;
    private String image;
    private CategoryDto category;
    private String description;
    private BrandDto brand;
    private String power;
    private Double weight;
    private Integer price;
    private Integer pledge;
}
