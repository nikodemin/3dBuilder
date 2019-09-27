package com.niko.prokat.Config;

import com.niko.prokat.Model.dto.BrandDto;
import com.niko.prokat.Model.dto.CategoryDto;
import com.niko.prokat.Model.dto.ToolDto;
import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.ToolService;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log4j2
public class SQLDataLoader implements ApplicationRunner {
    private final ToolService toolService;
    private final UserService userDetailsService;

    private BrandDto makita = new BrandDto();
    private BrandDto interskol = new BrandDto();
    private BrandDto hitachi = new BrandDto();
    private BrandDto bosh = new BrandDto();

    private CategoryDto cat0 = new CategoryDto();
    private CategoryDto cat1 = new CategoryDto();
    private CategoryDto cat2 = new CategoryDto();
    private CategoryDto cat3 = new CategoryDto();
    private CategoryDto cat4 = new CategoryDto();
    private CategoryDto cat5 = new CategoryDto();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        makita.setName("Makita");
        interskol.setName("Интерскол");
        hitachi.setName("Hitachi");
        bosh.setName("Bosh");

        toolService.addBrand(interskol);
        toolService.addBrand(hitachi);
        toolService.addBrand(bosh);

        cat0.setName("Электроинструменты");
        cat0.setImage("cat.jpg");
        cat1.setName("Строительное оборудование");
        cat1.setImage("cat.jpg");
        cat2.setName("Клининговое и климатическое оборудование");
        cat2.setImage("cat.jpg");
        cat3.setName("Силовая техника");
        cat3.setImage("cat.jpg");
        cat4.setName("Расходные материалы");
        cat4.setImage("cat.jpg");
        cat5.setName("Бензоинструменты");
        cat5.setImage("cat.jpg");

        Long catId = toolService.addCategory(cat0);
        toolService.addCategory(cat1);
        toolService.addCategory(cat2);
        toolService.addCategory(cat3);
        toolService.addCategory(cat4);
        toolService.addCategory(cat5);

        CategoryDto perf = new CategoryDto();
        perf.setName("Перфораторы");
        perf.setImage("cat.jpg");
        Long perfId = toolService.addSubCategory(perf, catId);

        ToolDto tool = new ToolDto();
        tool.setBrand(makita);
        tool.setCategory(perf);
        tool.setDescription("description");
        tool.setImage("cat.jpg");
        tool.setName("Makita mega");
        tool.setPledge(6000);
        tool.setPower("2kWt");
        tool.setPrice(600);
        tool.setWeight(1.0);
        toolService.addTool(tool);


        UserDto admin = new UserDto();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("dummy");
        userDetailsService.saveAdmin(admin);

        log.info("DATABASE FILLED!");
    }
}
