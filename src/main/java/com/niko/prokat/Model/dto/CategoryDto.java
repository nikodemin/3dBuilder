package com.niko.prokat.Model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {
    private String name;
    private String image;
    private Long id;
    List<CategoryDto> children = new ArrayList<>();
    CategoryDto parent;

    public void addChild(CategoryDto child){
        children.add(child);
        child.setParent(this);
    }
}
