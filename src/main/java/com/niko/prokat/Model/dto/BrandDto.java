package com.niko.prokat.Model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BrandDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String site;
}
