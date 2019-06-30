package com.dBuider.app;

import com.dBuider.app.Config.PropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(PropertiesConfig.class)
public class AppApplication
{
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
}
