package com.hp.ssm.controller;

import com.hp.ssm.model.Express;
import com.hp.ssm.model.Mission;
import com.hp.ssm.service.ExpressService;
import com.hp.ssm.service.MissionService;
import com.hp.ssm.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("express")
public class ExpressController {
    @Autowired
    private ExpressService expressService;
    @Autowired
    private MissionService missionService;

    @GetMapping("/add")
    public String getAddExpressPage() {
        return "/express/sendExpress";
    }

    @PostMapping("/add")
    public String addExpressPage(Express express, HttpSession session) {
        String uuid = UUID.randomUUID().toString();
        express.setUuid(uuid);
        expressService.addExpress(express);
        SmsUtil.sendSmsText(express.getSendPhone(), "提醒：您的快递 " + express.getName() + " to:" + express.getReceiveName() + " 已寄出,您的快递号为" + uuid);
        SmsUtil.sendSmsText(express.getReceivePhone(), "提醒：您的快递 " + express.getName() + " from:" + express.getSendName() + " 已寄出,您的快递号为" + uuid);
        session.setAttribute("uuid", uuid);
        return "/express/expressResponse";
    }

    @GetMapping("/list/{userId}")
    public String getExpressListByUserId(@PathVariable("userId") Integer userId, Model model) {
        List<Express> expressList = expressService.findAllExpressListByUserId(userId);
        if (expressList != null && !expressList.isEmpty()) {
            model.addAttribute("expressList", expressList);
        }
        return "/express/personalExpressList";
    }

    @GetMapping("/detail/{uuid}")
    public String getExpressListDetail(@PathVariable("uuid") String uuid, Model model) {
        if (uuid != null && !"".equals(uuid)) {
            Express express = expressService.getExpressDetail(uuid);
            if (express != null) {
                List<Mission> missions = missionService.getMissionsByUUID(express.getUuid());
                model.addAttribute("express", express);
                model.addAttribute("missions", missions);
            }
        }
        return "/express/expressDetail";
    }

    @InitBinder
    protected void init(ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
