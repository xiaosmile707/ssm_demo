package com.hp.ssm.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomizeErrorController implements ErrorController {

    @RequestMapping("/error")
    public String errorHandler(HttpServletRequest request) {
        Object ststus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (ststus != null) {
            Integer statusCode = Integer.valueOf(ststus.toString());
            switch (statusCode) {
                case 404:
                    return "404";
                case 500:
                    return "500";
                default:
                    return "error";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
