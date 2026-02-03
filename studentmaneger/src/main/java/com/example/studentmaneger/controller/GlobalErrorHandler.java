package com.example.studentmaneger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalErrorHandler {

    @RequestMapping("/custom-error")
    public String handleError() {
        return "error";
    }
}
