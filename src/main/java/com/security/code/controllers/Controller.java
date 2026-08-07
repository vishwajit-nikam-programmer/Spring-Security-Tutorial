package com.security.code.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greet")
public class Controller {
    @GetMapping("hello")
    public String hello(){
        return "Hello World!";
    }

    @GetMapping("hey")
    public String hey(){
        return "Hey World!";
    }

    @GetMapping("hii")
    public String hii(){
        return "Hii World!";
    }
    @GetMapping("/csrf")
    public CsrfToken getCsrf(HttpServletRequest request){
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
