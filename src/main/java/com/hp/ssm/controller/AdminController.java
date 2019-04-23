package com.hp.ssm.controller;

import com.hp.ssm.model.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/login")
    public String getAdminLoginPage(){
        return "/admin/login";
    }
    @PostMapping("/login")
    public String adminLogin(Admin admin, Model model){
        if (admin == null) {
            model.addAttribute("errorMsg","输入错误");
            return "redirect:/admin/login";
        }
        if (admin.getAccount() == null || "".equals(admin.getAccount())) {
            model.addAttribute("errorMsg","账号为空");
            return "redirect:/admin/login";
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            model.addAttribute("errorMsg","密码为空");
            return "redirect:/admin/login";
        }


        return "";
    }
}
