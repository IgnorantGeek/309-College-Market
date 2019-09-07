package cmart.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController
{
    @RequestMapping("/home")
    public String home()
    {
        return "ayo";
    }
}