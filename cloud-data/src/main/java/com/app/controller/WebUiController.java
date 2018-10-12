/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kerch
 */
@Controller
public class WebUiController {

    @RequestMapping(value="/welcome", method = RequestMethod.GET)
     public String sayWelcome(Model m){
         m.addAttribute("message", "hi");
        return "welcome";
    }
    @GetMapping("/login")
    public String getLogInPage(){
        return "login";
    }
    
}
