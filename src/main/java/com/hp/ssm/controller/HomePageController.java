package com.hp.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/home")
    public String getHomePage(){
        Integer i = null;
        int j = i;
        return "homePage";
    }
}
