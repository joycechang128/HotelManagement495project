package com.cmsc495.hotelmanagementapp.hello;
/*
 * File: WelcomeController.java
 * Path: src/main/java/com/cmsc495/hotelmanagementapp/hello/WelcomeController.java
 * Package: com.cmsc495.hotelmanagementapp.hello
 * Author: Chia-Yu(Joyce) Chang
 * Created: 2024-03-23
 * Last Modified: 2024-03-23
 * Description: This file contains the Java controller class for testing the Hello World.
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld()
    {
        return "Welcome to LuxuRest management system, please add /login to your URL to login";
    }
}

