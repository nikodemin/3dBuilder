package com.niko.prokat.Model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ToolDto {
    private Long id;
    private Long categoryID;
    private Long brandId;
    private BrandDto brand;
    private String name;
    private String prevImagePath;
    private String image1Path;
    private String image2Path;
    private String image3Path;
    private String description;
    private String power;
    private Integer weight;
    private Integer price;
    private Integer pledge;
    private MultipartFile prevImage;
    private MultipartFile[] images;
    private Integer quantity;
}
