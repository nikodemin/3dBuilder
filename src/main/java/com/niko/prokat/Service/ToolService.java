package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.BrandDto;
import com.niko.prokat.Model.dto.CategoryDto;
import com.niko.prokat.Model.dto.ToolDto;
import com.niko.prokat.Model.entity.Brand;
import com.niko.prokat.Model.entity.Category;
import com.niko.prokat.Model.entity.Order;
import com.niko.prokat.Model.entity.Tool;
import com.niko.prokat.Repo.BrandRepo;
import com.niko.prokat.Repo.CategoryRepo;
import com.niko.prokat.Repo.OrderRepo;
import com.niko.prokat.Repo.ToolRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ToolService {
    private final BrandRepo brandRepo;
    private final ToolRepo toolRepo;
    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;
    private final DtoMapper mapper;

    public List<ToolDto> findTools(Long categoryId) {
        if (categoryId == null) {
            log.error("No category specified");
            return null;
        }

        Category cat = categoryRepo.findById(categoryId).orElse(null);

        if (cat == null) {
            log.error("No category found");
            return null;
        }

        return toolRepo.findByCategory(cat).stream()
                .map(mapper::toToolDto).collect(Collectors.toList());
    }

    public List<ToolDto> findTools(String tool) {
        return toolRepo.findByNameContainingIgnoreCase(tool).stream().map(mapper::toToolDto)
                .collect(Collectors.toList());
    }

    public ToolDto findToolById(Long id) {
        Optional<Tool> tool = toolRepo.findById(id);
        return mapper.toToolDto(tool.orElse(null));
    }

    public List<ToolDto> getTopTools() {
        //todo check this
        Pageable last = PageRequest.of(0, 100, Sort.by("date").descending());
        List<Tool> tools = orderRepo.findAll(last).getContent().stream()
                .flatMap(o->o.getTools().stream()).collect(Collectors.toList());

        //map of tools and quantities
        Map<Tool, Integer> map = new HashMap<>();
        for (Tool tool : tools) {
            if (map.containsKey(tool))
                map.put(tool, map.get(tool) + 1);
            else
                map.put(tool, 1);
        }

        return map.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .limit(8)
                .map(e ->mapper.toToolDto(e.getKey()))
                .collect(Collectors.toList());
    }

    public List<BrandDto> getBrands() {
        return ((List<Brand>) brandRepo.findAll()).stream()
                .map(mapper::toBrandDto).collect(Collectors.toList());
    }

    public List<CategoryDto> getRootCategories() {
        return categoryRepo.findCategoriesByChildren_Empty().stream()
                .map(mapper::toCategoryDto).collect(Collectors.toList());
    }

    public boolean addTool(ToolDto tool) {
        try {
            return toolRepo.save(mapper.toTool(tool)) != null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    public boolean addBrand(BrandDto brand) {
        try {
            return brandRepo.save(mapper.toBrand(brand)) != null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    public boolean addCategory(CategoryDto category) {
        try {
            return categoryRepo.save(mapper.toCategory(category)) != null;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    public CategoryDto getCategory(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        return mapper.toCategoryDto(category.orElse(null));
    }

    public CategoryDto getCategory(List<Long> cat) {
        if (cat == null || cat.isEmpty()){
            log.error("No categories id specified");
            return null;
        }

        Category category = categoryRepo.findById(cat.get(0)).orElse(null);

        for (int i = 1; i < cat.size(); i++) {
            int finalI = i;
            if (category == null){
                log.error("Category is not exist");
                return null;
            }
            category = category.getChildren().stream()
                    .filter(c->c.getId().equals(cat.get(finalI)))
                    .findFirst().orElse(null);
        }

        return mapper.toCategoryDto(category);
    }
}
