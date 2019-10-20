package com.niko.prokat.Controller.usual;

import com.niko.prokat.Model.dto.CategoryDto;
import com.niko.prokat.Model.dto.ToolDto;
import com.niko.prokat.Service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ToolContoller {
    private final ToolService toolService;
    private final JavaMailSender mailer;

    @GetMapping("/catalog")
    public String getCategoriesPage(@RequestParam List<Long> cat,
                                    Model model){
        CategoryDto category = toolService.getCategory(cat);
        List<String> categoryIds = cat.stream()
                .map(String::valueOf).collect(Collectors.toList());
        model.addAttribute("queryStr",String.join(",",categoryIds));

        if (category.getChildren().isEmpty()){
            List<ToolDto> tools = toolService.findTools(category.getId());
            if (tools == null){
                model.addAttribute("text","404 - Страница не найдена");
                return "text";
            }

            model.addAttribute("tools",tools);
            model.addAttribute("catDesc",category.getDescription());
            return "catalog";
        }
        model.addAttribute("categories",category.getChildren());
        return "catalog";
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        model.addAttribute("topTools",toolService.getTopTools());
        model.addAttribute("sliderImgs", Arrays.asList("slider_main/baner1.jpg",
                "slider_main/baner2.jpg","slider_main/baner3.jpg","slider_main/baner4.jpg",
                "slider_main/baner5.jpg","slider_main/baner6.jpg"));
        return "main";
    }

    @GetMapping("/company")
    public String getCompanyPage(Model model){
        model.addAttribute("sliderImgs", Arrays.asList("slider_companies/firmi1.jpg",
                "slider_companies/firmi2.jpg","slider_companies/firmi3.jpg",
                "slider_companies/firmi4.jpg"));
        return "company";
    }

    @GetMapping("/tool/{toolId}")
    public String getTool(@PathVariable Long toolId,
                          Model model){
        model.addAttribute("tool",toolService.findToolById(toolId));
        model.addAttribute("available",
                toolService.howMuchToolsAvailable(toolId,null));
        return "tool";
    }

    @GetMapping("/search")
    public String searchTools(Model model, @RequestParam("search") String str){
        List<ToolDto> tools = toolService.findTools(str);
        model.addAttribute("tools", tools);
        if (tools.isEmpty()){
            model.addAttribute("toolsEmpty",true);
        }
        return "catalog";
    }
}
