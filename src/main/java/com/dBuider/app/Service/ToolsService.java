package com.dBuider.app.Service;

import com.dBuider.app.Model.Brand;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Repo.BrandRepo;
import com.dBuider.app.Repo.OrderRepo;
import com.dBuider.app.Repo.ToolRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ToolsService implements com.dBuider.app.Service.Interfaces.ToolsService
{
    private final BrandRepo brandRepo;
    private final ToolRepo toolRepo;
    private final OrderRepo orderRepo;

    @Override
    public List<Tool> findTools(String category, String subcategory)
    {
        List<Tool> tools = toolRepo.findByCategory(category);

        if (subcategory == null || tools == null)
            return tools;

        tools = tools.stream().filter(e->e.getSubcat().equals(subcategory))
                .collect(Collectors.toList());

        return tools;
    }

    @Override
    public List<Tool> findTools(String tool)
    {
        return toolRepo.findByNameContainingIgnoreCase(tool);
    }

    @Override
    public List<Tool> getTopTools()
    {
        Pageable last = PageRequest.of(0,100, Sort.by("date").descending());
        List<Order> orders = ((Page<Order>) orderRepo.findAll(last)).getContent();
        orders = Arrays.stream(orders.toArray(Order[]::new)).distinct().collect(Collectors.toList());

        return orders.stream().map(o->o.getTool()).collect(Collectors.toList());
    }

    @Override
    public List<Brand> getBrands()
    {
        return (List<Brand>)brandRepo.findAll();
    }

    @Override
    public List<String> getCategories()
    {
        return toolRepo.findCategories();
    }

    @Override
    public List<String> getSubCategories()
    {
        return toolRepo.findSubCategories();
    }

    @Override
    public void addTool(Tool tool)
    {
        if (!((List<Tool>)toolRepo.findAll()).contains(tool))
            toolRepo.save(tool);
        else
            log.error("Tool "+tool.getName()+" already exists!");
    }

    @Override
    public void addBrand(Brand brand)
    {
        if (!getBrands().contains(brand))
            brandRepo.save(brand);
        else
            log.error("Brand "+brand.getName()+" already exists!");
    }
}
