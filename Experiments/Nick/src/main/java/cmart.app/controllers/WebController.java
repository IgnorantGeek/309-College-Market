package cmart.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for navigating and using the web app
 */
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

    @RequestMapping("/test")
    public String test()
    {
        return "test";
    }
}