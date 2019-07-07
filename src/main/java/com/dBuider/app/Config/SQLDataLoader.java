package com.dBuider.app.Config;

import com.dBuider.app.Model.*;
import com.dBuider.app.Service.Interfaces.OrderService;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.Interfaces.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Log4j2
public class SQLDataLoader implements ApplicationRunner
{
    private final ToolsService toolsService;
    private final OrderService orderService;
    private final UserDetailsService userDetailsService;

    private Brand makita = new Brand("Makita","");
    private Brand interskol = new Brand("Интерскол","");
    private Brand hitachi = new Brand("Hitachi","");
    private Brand bosh = new Brand("Bosh","");

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        toolsService.addBrand(makita);
        toolsService.addBrand(interskol);
        toolsService.addBrand(hitachi);
        toolsService.addBrand(bosh);

        toolsService.addTool(new Tool("img","perforatori",
                "","Makita mega","desc",makita,
                "2kWt",1.0, 600, 6000));
        toolsService.addTool(new Tool("img","perforatori",
                "","Bosh mega","desc",bosh,
                "2kWt",1.0, 400, 4000));
        toolsService.addTool(new Tool("img","perforatori",
                "","Interskol mega","desc",interskol,
                "2kWt",1.0, 300, 3000));
        toolsService.addTool(new Tool("img","perforatori",
                "","Hitachi mega","desc",hitachi,
                "2kWt",1.0, 600, 6000));

        User vasya = new User("Vasya","123","SPB",
                "8911","mail","Vasya","Bobkin");
        userDetailsService.saveUser(vasya);

        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Makita mega").get(0),
                vasya, new Date(), 3, false));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Bosh mega").get(0),
                vasya, new Date(), 1, true));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Interskol mega").get(0),
                vasya, new Date(), 5, false));
        orderService.addOrder(new Order("SPB",
                toolsService.findTools("Hitachi mega").get(0),
                vasya, new Date(), 4, true));

        User admin = new Admin("admin","admin",
                "","","","","");
        userDetailsService.saveUser(admin);

        log.info("DATABASE FILLED!");
    }
}
