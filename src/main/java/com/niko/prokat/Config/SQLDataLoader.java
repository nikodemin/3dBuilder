package com.niko.prokat.Config;

import com.niko.prokat.Model.dto.UserDto;
import com.niko.prokat.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class SQLDataLoader implements ApplicationRunner {
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        if (userService.getUser("admin") == null) {
            UserDto admin = new UserDto();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("niko.demin@gmail.com");
            userService.saveAdmin(admin);
            log.info("ADMIN ADDED!");
        }
    }
}
