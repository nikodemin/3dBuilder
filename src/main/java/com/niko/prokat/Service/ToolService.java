package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.*;
import com.niko.prokat.Model.entity.Brand;
import com.niko.prokat.Model.entity.Category;
import com.niko.prokat.Model.entity.Tool;
import com.niko.prokat.Repo.BrandRepo;
import com.niko.prokat.Repo.CategoryRepo;
import com.niko.prokat.Repo.OrderRepo;
import com.niko.prokat.Repo.ToolRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

        if (categoryId == -2L){
            return toolRepo.findByCategoryIsNull().stream()
                    .map(mapper::toToolDto).collect(Collectors.toList());
        }

        if (categoryId == -3L){
            return toolRepo.findByBrandIsNull().stream()
                    .map(mapper::toToolDto).collect(Collectors.toList());
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
        return categoryRepo.findCategoryByIsRootIsTrue().stream()
                .map(mapper::toCategoryDto).collect(Collectors.toList());
    }

    public Long addBrand(BrandDto brand) {
        return brandRepo.save(mapper.toBrand(brand)).getId();
    }

    public Long addCategory(CategoryDto category) {
        return categoryRepo.save(mapper.toCategory(category)).getId();
    }

    public Long addSubCategory(CategoryDto categoryDto, Long catId){
        Category category = mapper.toCategory(categoryDto);
        Category parent = categoryRepo.findById(catId).orElse(null);

        if (parent == null){
            log.error("Category not found");
            return null;
        }

        parent.getChildren().add(category);
        Long id = categoryRepo.save(category).getId();
        categoryRepo.save(parent);
        categoryDto.setId(id);
        return id;
    }

    public Long addTool(ToolDto toolDto) {
        Tool tool = mapper.toTool(toolDto);
        return toolRepo.save(tool).getId();
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

    public List<TreeNodeDto> getCategoriesTree() {
        TreeNodeDto root = new TreeNodeDto();
        root.setId(-1L);
        root.setText("Категории");
        root.setChildren(categoryRepo.findCategoryByIsRootIsTrue().stream()
                .map(mapper::toTreeNodeDto).collect(Collectors.toList()));
        TreeNodeDto detachedTools = new TreeNodeDto();
        detachedTools.setId(-2L);
        detachedTools.setText("Отсоединённые инструменты");
        TreeNodeDto noCategoryTools = new TreeNodeDto();
        noCategoryTools.setId(-3L);
        noCategoryTools.setText("Инструменты без категории");
        return Arrays.asList(root,detachedTools,noCategoryTools);
    }

    public void removeCategory(Long id) {
        Category category = categoryRepo.findById(id).get();
        List<Category> leafs = new LinkedList<>();

        getCategoryTreeLeafs(leafs, category);

        for (Category leaf:leafs) {
            for (Tool tool:toolRepo.findByCategory(leaf)) {
                tool.setCategory(null);
                toolRepo.save(tool);
            }
        }
        Category parent = categoryRepo.findCategoryByChildrenContaining(category);
        if (parent != null) {
            parent.getChildren().remove(category);
            categoryRepo.save(parent);
        }
        categoryRepo.deleteById(id);
    }

    private void getCategoryTreeLeafs(List<Category> leafs, Category category){
        if (category.getChildren() == null ||
                category.getChildren().isEmpty()) {
            leafs.add(category);
        } else {
            for (Category child : category.getChildren()) {
                getCategoryTreeLeafs(leafs, child);
            }
        }
    }

    public void renameCategory(Long id, String name) {
        Category category = categoryRepo.findById(id).get();
        category.setName(name);
        categoryRepo.save(category);
    }

    public List<CategoryDto> getAllCategories() {
        return ((List<Category>) categoryRepo.findAll()).stream()
                .map(mapper::toCategoryDto).collect(Collectors.toList());
    }

    public List<CategoryDto> getCategoriesLeafs() {
        List<Category> leafs = new ArrayList<>();
        categoryRepo.findCategoryByIsRootIsTrue()
                .forEach(c->getCategoryTreeLeafs(leafs,c));
        return leafs.stream().map(mapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public boolean areThereToolsInCategory(Long id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category == null)
            return false;
        return toolRepo.existsByCategory(category);
    }

    public void detachTool(Long id) {
        Tool tool = toolRepo.findById(id).get();
        tool.setCategory(null);
        toolRepo.save(tool);
    }

    public void removeTool(Long id) {
        toolRepo.deleteById(id);
    }

    public void updateTool(Long id, ToolDto toolDto) {
        Tool tool = toolRepo.findById(id).get();
        tool.setCategory(categoryRepo.findById(toolDto.getCategoryID()).get());
        tool.setName(toolDto.getName());
        tool.setBrand(brandRepo.findById(toolDto.getBrandId()).get());
        tool.setDescription(toolDto.getDescription());
        tool.setPrevImage(toolDto.getPrevImagePath());
        tool.setImage1(toolDto.getImage1Path());
        tool.setImage2(toolDto.getImage2Path());
        tool.setImage3(toolDto.getImage3Path());
        tool.setPower(toolDto.getPower());
        tool.setPrice(toolDto.getPrice());
        tool.setPledge(toolDto.getPledge());
        tool.setWeight(toolDto.getWeight());
        tool.setQuantity(toolDto.getQuantity());
        toolRepo.save(tool);
    }

    public ToolDto getTool(Long id) {
        return mapper.toToolDto(toolRepo.findById(id).get());
    }

    public void updateBrand(BrandDto brandDto, Long id) {
        Brand brand = brandRepo.findById(id).get();
        if (brandDto.getName() != null || brandDto.getName() != "") {
            brand.setName(brandDto.getName());
        }
        if (brandDto.getSite() != null || brandDto.getSite() != ""){
            brand.setSite(brandDto.getSite());
        }
        brandRepo.save(brand);
    }

    public void removeBrand(Long id) {
        List<Tool> tools = toolRepo.findByBrand(brandRepo.findById(id).get());
        tools.forEach(tool -> {
            tool.setBrand(null);
            toolRepo.save(tool);
        });
        brandRepo.deleteById(id);
    }

    public Integer howMuchToolsAvailable(Long id, OrderDto orderDto) {
        final Integer[] quantity = {toolRepo.findById(id).get().getQuantity()};
        /*orderRepo.findByDoneIsFalse().forEach(order -> {
            order.getTools().forEach(tool -> {
                if (tool.getId().equals(id)){
                    --quantity[0];
                }
            });
        });*/
        if (orderDto != null) {
            orderDto.getTools().forEach(tool -> {
                if (tool.getId().equals(id)) {
                    --quantity[0];
                }
            });
        }
        return quantity[0];
    }

    public void changeCatDesc(Long id, String desc) {
        Category category = categoryRepo.findById(id).get();
        category.setDescription(desc);
        categoryRepo.save(category);
    }
}
