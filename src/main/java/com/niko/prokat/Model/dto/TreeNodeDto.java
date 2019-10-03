package com.niko.prokat.Model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNodeDto {
    private Long id;
    private String text;
    private List<TreeNodeDto> children = new ArrayList<>();
}
