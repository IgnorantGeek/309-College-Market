package cmart.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController
{
    @RequestMapping("/front")
    public String front()
    {
        return "front";
    }

    @RequestMapping("/back")
    public String back()
    {
        return "back";
    }
}