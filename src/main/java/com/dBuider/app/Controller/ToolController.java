package com.dBuider.app.Controller;

import com.dBuider.app.Service.Interfaces.ToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tool")
@RequiredArgsConstructor
public class ToolController
{
    private final ToolsService toolsService;

    @GetMapping("/{id}")
    public String showTool(@PathVariable("id") String id,
                           Model model)
    {
        model.addAttribute("tool", toolsService.findToolById(id));
        return "tool";
    }
}
