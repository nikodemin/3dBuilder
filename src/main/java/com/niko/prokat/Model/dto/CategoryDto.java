package com.niko.prokat.Model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private String name;
    private String image;
    private Long id;
    List<CategoryDto> children;
}
