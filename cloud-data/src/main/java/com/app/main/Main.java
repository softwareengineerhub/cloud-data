/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.main;

import com.app.config.AppConfig;
import com.app.config.ThymeleafConfig;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author kerch
 */
public class Main {
    
    public static void main(String[] args) {
        SpringApplication.run(new Object[]{AppConfig.class,ThymeleafConfig.class}, args);
    }
    
}
