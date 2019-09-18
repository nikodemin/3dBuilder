package com.niko.prokat.Service;

import com.niko.prokat.Model.dto.*;
import com.niko.prokat.Model.entity.*;
import com.niko.prokat.Model.enums.UserRole;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    BrandDto toBrandDto(Brand brand);

    default CategoryDto toCategoryDto(Category category){
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

        return categoryDto;
    }

    OrderDto toOrderDto(Order order);

    ToolDto toToolDto(Tool tool);

    UserDto toUserDto(User user);

    Brand toBrand(BrandDto brandDto);

    Category toCategory(CategoryDto categoryDto);

    Order toOrder(OrderDto orderDto);

    Tool toTool(ToolDto toolDto);

    User toUser(UserDto userDto);

    default User toAdmin(UserDto userDto){
        if (userDto == null){
            return null;
        }

        User admin = toUser(userDto);
        admin.setRole(UserRole.ADMIN);
        return admin;
    }
}
