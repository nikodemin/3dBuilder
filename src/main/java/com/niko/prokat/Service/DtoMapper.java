package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.*;
import com.niko.prokat.Model.entity.*;
import com.niko.prokat.Model.enums.OrderStatus;
import com.niko.prokat.Repo.BrandRepo;
import com.niko.prokat.Repo.CategoryRepo;
import com.niko.prokat.Repo.ToolRepo;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
    private BrandRepo brandRepo;

    public abstract BrandDto toBrandDto(Brand brand);

    public abstract CategoryDto toCategoryDto(Category category);

    protected List<CategoryDto> categoryListToCategoryDtoList(List<Category> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryDto> list1 = new ArrayList<CategoryDto>( list.size() );
        for ( Category category : list ) {
            list1.add( toCategoryDto( category ) );
        }

        //sort by sort index
        list.sort(Comparator.comparing(Category::getSortIndex));
        return list1;
    }

    public abstract OrderDto toOrderDto(Order order);

    @Mapping(source = "prevImage",target = "prevImagePath")
    @Mapping(target = "prevImage", ignore = true)
    @Mapping(source = "image1",target = "image1Path")
    @Mapping(source = "image2",target = "image2Path")
    @Mapping(source = "image3",target = "image3Path")
    public abstract ToolDto toToolDto(Tool tool);

    public abstract UserDto toUserDto(User user);

    public abstract Brand toBrand(BrandDto brandDto);

    public abstract Category toCategory(CategoryDto categoryDto);

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
        Category category = categoryRepo.findById(toolDto.getCategoryID())
                .orElse(null);

        if (category == null) {
            log.error("No category found");
            return null;
        }
        tool.setId( toolDto.getId() );
        tool.setPrevImage( toolDto.getPrevImagePath() );
        tool.setImage1(toolDto.getImage1Path());
        tool.setImage2(toolDto.getImage2Path());
        tool.setImage3(toolDto.getImage3Path());
        tool.setCategory(category);
        tool.setName( toolDto.getName() );
        tool.setDescription( toolDto.getDescription() );
        tool.setBrand( brandRepo.findById(toolDto.getBrandId()).get());
        tool.setPower( toolDto.getPower() );
        tool.setWeight( toolDto.getWeight() );
        tool.setPrice( toolDto.getPrice() );
        tool.setPledge( toolDto.getPledge() );
        tool.setQuantity(toolDto.getQuantity());

        return tool;
    }

    public abstract User toUser(UserDto userDto);

    @Mapping(source = "name", target = "text")
    public abstract TreeNodeDto toTreeNodeDto(Category c);
}
