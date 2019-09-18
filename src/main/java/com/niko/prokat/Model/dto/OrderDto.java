package com.niko.prokat.Model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class OrderDto {
    @NotBlank(message = "Адрес не указан")
    private String address;

    @NotBlank(message = "Кол-во дней не указано")
    private Integer fordays;

    @NotBlank(message = "Дата не указана")
    private String date;
    private List<ToolDto> tools = new ArrayList();

    public List<TulipDto<ToolDto,Integer>> getUniqTools(){
        Map<ToolDto,Integer> map = new HashMap<>();

        for (ToolDto tool:tools) {
            if (map.containsKey(tool)) {
                map.put(tool, map.get(tool) + 1);
            } else {
                map.put(tool, 1);
            }
        }

        return map.entrySet().stream()
                .map(e->new TulipDto<>(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }
}
