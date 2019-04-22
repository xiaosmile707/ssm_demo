package com.hp.ssm.controller;

import com.hp.ssm.model.Express;
import com.hp.ssm.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("express")
public class ExpressController {
    @Autowired
    private ExpressService expressService;

    @GetMapping("/add")
    public String getAddExpressPage(){
        return "/express/sendExpress";
    }
    @PostMapping("/add")
    public String addExpressPage(Express express, HttpSession session) {
        String uuid = UUID.randomUUID().toString();
        express.setUuid(uuid);
        expressService.addExpress(express);
        session.setAttribute("uuid",uuid);
        return "/express/expressResponse";
    }

    @InitBinder
    protected void init(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
