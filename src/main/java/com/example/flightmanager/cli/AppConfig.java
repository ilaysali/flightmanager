package com.example.flightmanager.cli;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.example.flightmanager") // Scans all packages (dao, service, cli)
@PropertySource("classpath:application.properties") // Loads the settings file
public class AppConfig {
}