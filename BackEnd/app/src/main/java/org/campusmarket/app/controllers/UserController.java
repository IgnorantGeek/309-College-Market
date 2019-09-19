package org.campusmarket.app.controllers;

import java.util.List;

import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    UsersRepository users;

    @RequestMapping("/all")
    public List<User> getAll()
    {
        return users.findAll();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findUser(@PathVariable("id") int id)
    {
        return users.getOne(id);
    }
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void newUser(@RequestBody final User user)
    {
        users.save(user);
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String defaultHello(@RequestParam(name = "name", defaultValue = "Dave", required = false) String name)
    {
        return "Hello there, " + name + "!";
    }
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name)
    {
        return "Hello there, " + name + "!";
    }
}