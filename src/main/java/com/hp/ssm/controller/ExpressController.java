package com.hp.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("express")
public class ExpressController {
    @GetMapping("/add")
    public String getAddExpressPage(){
        return "/express/sendExpress";
    }
}
