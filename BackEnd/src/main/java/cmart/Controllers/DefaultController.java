package cmart.Controllers;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cmart.Classes.User;

@RestController
public class DefaultController
{
    @RequestMapping("/users/add")
    public User newUser(@RequestParam(value = "firstname", required = true) String firstname,
                        @RequestParam(value = "lastname", required = true) String lastname)
    {
        Random rand = new Random();
        int id = rand.nextInt(256);
        return new User(firstname, lastname, id);
    }
}