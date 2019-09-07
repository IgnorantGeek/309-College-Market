package cmart.app.controllers;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import cmart.app.classes.*;

/**
 * Controller for the RestAPI to communicate with the mobile app
 */
@RestController
@RequestMapping("/api")
public class ApiController
{
    @RequestMapping("/home")
    public String home()
    {
        return "ayo";
    }

    @RequestMapping("/testUser")
    public User testUser(@RequestParam(value = "name", defaultValue = "JohnDoe", required = false) String name)
    {
        Random rand = new Random();
        User u = new User(name, rand.nextInt(1024));
        Post p = new Post();
        u.addPost(p);
        return u;
    }
}