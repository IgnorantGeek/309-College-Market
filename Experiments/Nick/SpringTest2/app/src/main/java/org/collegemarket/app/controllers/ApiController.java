package org.collegemarket.app.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/api") // This can be default to "/" because we dont care about web
public class ApiController
{
    @RequestMapping("")
    public String home()
    {
        return "Welcome to the CollegeMarket API.";
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String defaultHello()
    {
        return "Hello User! In the URL field above, enter your name after the slash!";
    }
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name)
    {
        return "Hello there, " + name + "!";
    }
}