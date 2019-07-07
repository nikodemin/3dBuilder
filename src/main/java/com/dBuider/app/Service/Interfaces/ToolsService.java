package com.dBuider.app.Service.Interfaces;

import com.dBuider.app.Model.Brand;
import com.dBuider.app.Model.Tool;

import java.util.List;

public interface ToolsService
{
    List<Tool> findTools(String category, String subcategory);
    List<Tool> findTools(String query);
    List<Tool> getTopTools();
    List<Brand> getBrands();
    List<String> getCategories();
    List<String> getSubCategories();
    void addTool(Tool tool);
    void addBrand(Brand brand);
}
