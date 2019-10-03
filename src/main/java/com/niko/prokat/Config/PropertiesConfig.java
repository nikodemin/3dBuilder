package com.niko.prokat.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
@ConfigurationProperties(prefix = "server")
@Data
@Primary
public class PropertiesConfig {
    private String email;
    private String emailPassword;
}
