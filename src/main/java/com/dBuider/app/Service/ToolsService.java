package com.dBuider.app.Service;

import com.dBuider.app.Model.Brand;
import com.dBuider.app.Model.Category;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Repo.BrandRepo;
import com.dBuider.app.Repo.CategoryRepo;
import com.dBuider.app.Repo.OrderRepo;
import com.dBuider.app.Repo.ToolRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ToolsService implements com.dBuider.app.Service.Interfaces.ToolsService
{
    private final BrandRepo brandRepo;
    private final ToolRepo toolRepo;
    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public List<Tool> findTools(String category, String subcategory)
    {
        Category cat = categoryRepo.findByNameIgnoreCaseAndSubcatIgnoreCase(category,subcategory);
        List<Tool> tools = toolRepo.findByCategory(cat);

        return tools;
    }

    @Override
    public List<Tool> findTools(String tool)
    {
        return toolRepo.findByNameContainingIgnoreCase(tool);
    }

    @Override
    public Tool findToolById(String id)
    {
        try
        {
            return toolRepo.findById(Long.parseLong(id)).get();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tool> getTopTools()
    {
        //todo check this
        Pageable last = PageRequest.of(0,100, Sort.by("date").descending());
        List<Order> orders = ((Page<Order>) orderRepo.findAll(last)).getContent();

        //map of orders and quantities
        Map<Order,Integer> map = new HashMap<>();
        for (Order o:orders)
        {
            if (map.containsKey(o))
                map.put(o,map.get(o)+1);
            else
                map.put(o,1);
        }

        List<Map.Entry<Order, Integer>> entryList = new ArrayList(map.entrySet());

        //sort by quantity
        entryList.sort((e1, e2) -> e1.getValue() - e2.getValue());

        return entryList.stream().limit(8).map(e->
                e.getKey().getTools().get(0)).collect(Collectors.toList());
    }

    @Override
    public List<Brand> getBrands()
    {
        return (List<Brand>)brandRepo.findAll();
    }

    @Override
    public List<Category> getCategories()
    {
        return (List<Category>)categoryRepo.findAll();
    }

    @Override
    public Tool addTool(Tool tool)
    {
        try
        {
            return toolRepo.save(tool);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Brand addBrand(Brand brand)
    {
        try
        {
            return brandRepo.save(brand);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category addCategory(Category category)
    {
        try
        {
            return categoryRepo.save(category);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
