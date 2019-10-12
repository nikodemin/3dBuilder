package com.niko.prokat.Model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {
    private String name;
    private String image;
    private Long id;
    private String description;
    private Boolean isRoot = false;
    private List<CategoryDto> children = new ArrayList<>();
    private MultipartFile file;
}
