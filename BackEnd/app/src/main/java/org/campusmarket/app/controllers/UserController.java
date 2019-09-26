package org.campusmarket.app.controllers;

import java.util.List;

import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UsersRepository users;
    
    
   @RequestMapping("/all")
    public List<User> getAll()
    {
        return users.findAll();
    }
     
    @GetMapping("id/{id}")
    public User findUserById(@PathVariable("id") int id)
    {
        return users.findById(id);
        
    }
    
    @GetMapping("email/{email}")
    public User findUserByEmail(@PathVariable("email") String email)
    {
        return users.findByEmail(email);
        
    }

    @GetMapping("username/{username}")
    public User findUserByUserName(@PathVariable("username") String username)
    {
        return users.findByUsername(username);
        
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void newUser(@RequestBody final User user)
    {
        users.save(user);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id)
    {
         users.deleteById(id);    
    }
    
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAll()
    {
         users.deleteAll();   
    }

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name)
    {
        return "Hello there, " + name + "!";
    }
}