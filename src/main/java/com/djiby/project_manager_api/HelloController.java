package com.djiby.project_manager_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Project Manager API is running";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }
}