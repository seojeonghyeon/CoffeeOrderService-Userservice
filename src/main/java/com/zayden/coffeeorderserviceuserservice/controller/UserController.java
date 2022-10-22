package com.zayden.coffeeorderserviceuserservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private Environment env;

    @Autowired
    public UserController(Environment env){
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status(){
        return String.format("HEALTH CHECK : User-Serivce UserController" +
                "[ "
                +"port(local.server.port)=" + env.getProperty("local.server.port")
                +", port(server.port)=" + env.getProperty("server.port")+"" +
                " ]");
    }

}
