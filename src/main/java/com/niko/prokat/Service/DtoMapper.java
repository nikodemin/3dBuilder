package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.*;
import com.niko.prokat.Model.entity.*;
import com.niko.prokat.Model.enums.OrderStatus;
import com.niko.prokat.Repo.CategoryRepo;
import com.niko.prokat.Repo.ToolRepo;
import com.niko.prokat.Repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Mapper(componentModel = "spring")
public abstract class DtoMapper {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ToolRepo toolRepo;
    @Autowired
    private UserRepo userRepo;

    public abstract BrandDto toBrandDto(Brand brand);

    public CategoryDto toCategoryDto(Category category){
        if (category == null) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setImage(category.getImage());
        categoryDto.setName(category.getName());
        categoryDto.setId(category.getId());
        categoryDto.setChildren(category.getChildren().stream().map(c->{
          CategoryDto child = new CategoryDto();
          child.setId(c.getId());
          child.setName(c.getName());
          child.setImage(c.getImage());
          return child;
        }).collect(Collectors.toList()));
        categoryDto.setParent(toCategoryDto(category.getParent()));

        return categoryDto;
    }

    public abstract OrderDto toOrderDto(Order order);

    public abstract ToolDto toToolDto(Tool tool);

    public abstract UserDto toUserDto(User user);

    public abstract Brand toBrand(BrandDto brandDto);

    public Category toCategory(CategoryDto categoryDto){
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( categoryDto.getId() );
        category.setName( categoryDto.getName() );
        category.setImage( categoryDto.getImage() );
        category.setChildren( categoryDtoListToCategoryList( categoryDto.getChildren() ) );

        CategoryDto parentDto = categoryDto.getParent();
        if (parentDto != null && parentDto.getId() != null) {
            Category parent = categoryRepo.findById(parentDto.getId()).orElse(null);
            category.setParent(parent);
        }

        return category;
    }

    protected abstract List<Category> categoryDtoListToCategoryList(List<CategoryDto> children);

    public Order toOrder(OrderDto orderDto){
        if ( orderDto == null ) {
            return null;
        }

        Order order = new Order();

        Integer[] total = new Integer[1];
        Integer[] pledge = new Integer[1];
        total[0] = 0;
        pledge[0] = 0;
        List<Tool> tools = orderDto.getTools().stream().map(t->{
            Tool tool = toolRepo.findById(t.getId()).orElse(null);
            if (tool == null) {
                log.error("Tool not found");
                throw new RuntimeException("Tool not found");
            }
            total[0] += tool.getPrice()*orderDto.getFordays();
            pledge[0] += tool.getPledge();
            return tool;
        }).collect(Collectors.toList());
        order.setTools(tools);
        order.setAddress( orderDto.getAddress() );
        order.setFordays( orderDto.getFordays() );
        order.setDone(false);
        order.setStatus(OrderStatus.PROCESSING);
        order.setPledge(pledge[0]);
        order.setTotal(total[0]);
        //order.setUser(userRepo.findByUsername(orderDto.getUser().getUsername()));
        try {
            if ( orderDto.getDate() != null ) {
                order.setDate( new SimpleDateFormat("yyyy-MM-dd")
                        .parse( orderDto.getDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return order;
    }

    public Tool toTool(ToolDto toolDto){
        if ( toolDto == null ) {
            return null;
        }

        Tool tool = new Tool();
        Category category = categoryRepo.findById(toolDto.getCategory().getId())
                .orElse(null);

        if (category == null) {
            log.error("No category found");
            return null;
        }
        tool.setId( toolDto.getId() );
        tool.setImage( toolDto.getImage() );
        tool.setCategory(category);
        tool.setName( toolDto.getName() );
        tool.setDescription( toolDto.getDescription() );
        tool.setBrand( toBrand( toolDto.getBrand() ) );
        tool.setPower( toolDto.getPower() );
        tool.setWeight( toolDto.getWeight() );
        tool.setPrice( toolDto.getPrice() );
        tool.setPledge( toolDto.getPledge() );

        return tool;
    }

    public abstract User toUser(UserDto userDto);
}
