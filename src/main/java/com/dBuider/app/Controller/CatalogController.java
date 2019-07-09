package com.dBuider.app.Controller;

import com.dBuider.app.Model.Category;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.TranslitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController
{
    private final ToolsService toolsService;

    @GetMapping("/{category}/{subcat}")
    public String showTools(@PathVariable String category,
                            @PathVariable String subcat,
                            Model model)
    {
        List<Tool> tools = toolsService.findTools(TranslitService.toRus(category),
                TranslitService.toRus(subcat));

        model.addAttribute("tools",tools);
        return "catalog";
    }

    @GetMapping("/{category}")
    public String showCatalog(@PathVariable String category, Model model)
    {
        List<Category> categories = toolsService.
                getCategories().stream().filter(e->
            TranslitService.toRus(category)
                    .equals(e.getName().toLowerCase())
        ).collect(Collectors.toList());

        model.addAttribute("categories",categories);
        return "catalog";
    }
}
