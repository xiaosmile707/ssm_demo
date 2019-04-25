package com.hp.ssm.controller;

import com.hp.ssm.model.Admin;
import com.hp.ssm.model.Express;
import com.hp.ssm.model.User;
import com.hp.ssm.service.AdminService;
import com.hp.ssm.service.ExpressService;
import com.hp.ssm.service.UserService;
import com.hp.ssm.utils.JavaMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExpressService expressService;

    @GetMapping("/login")
    public String getAdminLoginPage() {
        return "/admin/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/admin/register";
    }

    @GetMapping("/dashboard")
    public String getAdminIndex(Model model, String address) {
        address = (address == null || "".equals(address)) ? "dashBoard" : address;
        model.addAttribute("pageContent", address);

        if ("1-1".equals(address)) {
            List<User> users = userService.getUnRNAuthUserListByUserType(0);
            model.addAttribute("userList", users);
        }
        if ("1-2".equals(address)) {
            List<User> users = userService.getAllUsersByUserType(0);
            model.addAttribute("userList", users);
        }
        if ("2-1".equals(address)) {
            List<User> users = userService.getUnRNAuthUserListByUserType(1);
            model.addAttribute("userList", users);
        }
        if ("2-2".equals(address)) {
            List<User> users = userService.getAllUsersByUserType(1);
            model.addAttribute("userList", users);
        }
        if ("3-1".equals(address)) {
            List<Admin> adminList = adminService.getUnActiveAdmins();
            model.addAttribute("adminList", adminList);
        }
        if ("3-2".equals(address)) {
            List<Admin> adminList = adminService.getAllAdmins();
            model.addAttribute("adminList", adminList);
        }
        if ("4-1".equals(address)) {
            List<Express> expressList = expressService.findAllExpressList();
            model.addAttribute("expressList", expressList);
        }
        return "/admin/index";
    }

    @PostMapping("/login")
    public String adminLogin(Admin admin, Model model, HttpSession session) {
        if (admin == null) {
            model.addAttribute("errorMsg", "输入错误");
            return "/admin/login";
        }
        if (admin.getAccount() == null || "".equals(admin.getAccount())) {
            model.addAttribute("errorMsg", "账号为空");
            return "/admin/login";
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            model.addAttribute("errorMsg", "密码为空");
            return "/admin/login";
        }
        Admin databaseAdmin = adminService.findAdminByAccount(admin.getAccount());
        if (databaseAdmin == null) {
            model.addAttribute("errorMsg", "账户不存在");
            return "/admin/login";
        }
        if (databaseAdmin.getActive() == null || new Integer(0).equals(databaseAdmin.getActive())) {
            model.addAttribute("errorMsg", "账户未激活");
            return "/admin/login";
        }
        String comingPsd = JavaMD5.getMd5Code(admin.getPassword());
        if (!comingPsd.equals(databaseAdmin.getPassword())) {
            model.addAttribute("errorMsg", "密码错误");
            return "/admin/login";
        }
        session.setAttribute("user", databaseAdmin);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/register")
    public String adminRegister(Admin admin, Model model) {
        if (admin == null) {
            model.addAttribute("errorMsg", "输入错误");
            return "redirect:/admin/register";
        }
        if (admin.getAccount() == null || "".equals(admin.getAccount())) {
            model.addAttribute("errorMsg", "账号为空");
            return "redirect:/admin/register";
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            model.addAttribute("errorMsg", "密码为空");
            return "redirect:/admin/register";
        }
        boolean checkAdminIsExist = adminService.checkAdminIsExist(admin.getAccount());

        if (checkAdminIsExist) {
            model.addAttribute("errorMsg", "账号已存在");
            return "redirect:/admin/register";
        }

        admin.setPassword(JavaMD5.getMd5Code(admin.getPassword()));
        adminService.addAdmin(admin);
        model.addAttribute("errorMsg", "账号已注册成功，等待管理员审核");
        return "redirect:/admin/register";
    }

    @PostMapping("/updateUser")
    public String updateUser(String page, User user) {
        page = (page == null) ? "" : page;
        userService.updateUserSelective(user);
        return "redirect:/admin/dashboard?address=" + page;
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateAdminActive(@PathVariable("id") Integer id) throws Exception {
        if (id == null) {
            throw new Exception("id为空");
        }
        Admin admin = new Admin();
        admin.setId(id);
        admin.setActive(1);
        adminService.updateAdminSelective(admin);
    }
}
