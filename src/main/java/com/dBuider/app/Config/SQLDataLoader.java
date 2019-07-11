package com.dBuider.app.Config;

import com.dBuider.app.Model.*;
import com.dBuider.app.Service.Interfaces.OrderService;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
@Log4j2
public class SQLDataLoader implements ApplicationRunner
{
    private final ToolsService toolsService;
    private final OrderService orderService;
    private final UserRepositoryUserDetailsService userDetailsService;

    private Brand makita = new Brand("Makita","");
    private Brand interskol = new Brand("Интерскол","");
    private Brand hitachi = new Brand("Hitachi","");
    private Brand bosh = new Brand("Bosh","");

    private Category perf = new Category("Электроинструменты","/imgs/cat.jpg",
            "Перфораторы","/imgs/cat.jpg");

    private Category cat = new Category("Бензоинструменты","/imgs/cat.jpg",
            "Бензопилы","/imgs/cat.jpg");
    private Category cat2 = new Category("Строительное оборудование","/imgs/cat.jpg",
            "Уголки","/imgs/cat.jpg");
    private Category cat3 = new Category("Клининговое и климатическое оборудование","/imgs/cat.jpg",
            "Karcher","/imgs/cat.jpg");
    private Category cat4 = new Category("Силовая техника","/imgs/cat.jpg",
            "Генераторы","/imgs/cat.jpg");
    private Category cat5 = new Category("Расходные материалы","/imgs/cat.jpg",
            "Перчатки","/imgs/cat.jpg");

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        toolsService.addBrand(makita);
        toolsService.addBrand(interskol);
        toolsService.addBrand(hitachi);
        toolsService.addBrand(bosh);

        toolsService.addCategory(perf);
        toolsService.addCategory(cat);
        toolsService.addCategory(cat2);
        toolsService.addCategory(cat3);
        toolsService.addCategory(cat4);
        toolsService.addCategory(cat5);

        toolsService.addTool(new Tool("/imgs/cat.jpg",perf,"Makita mega","desc",makita,
                "2kWt",1.0, 600, 6000));
        toolsService.addTool(new Tool("/imgs/cat.jpg", perf,"Bosh mega","desc",bosh,
                "2kWt",1.0, 400, 4000));
        toolsService.addTool(new Tool("/imgs/cat.jpg",perf,"Interskol mega",
                "desc",interskol,
                "2kWt",1.0, 300, 3000));
        toolsService.addTool(new Tool("/imgs/cat.jpg",perf,
                "Hitachi mega","desc",hitachi,
                "2kWt",1.0, 600, 6000));

        User vasya = new User("Vasya","123","SPB",
                "8911","mail","Vasya","Bobkin");
        userDetailsService.saveUser(vasya);

        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Makita mega"),
                vasya, new Date(), 3, false));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Bosh mega"),
                vasya, new Date(), 1, true));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Interskol mega"),
                vasya, new Date(), 5, false));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Hitachi mega"),
                vasya, new Date(), 4, true));

        User admin = new Admin("admin","admin",
                "","","","","");
        userDetailsService.saveUser(admin);

        log.info("DATABASE FILLED!");
    }
}
