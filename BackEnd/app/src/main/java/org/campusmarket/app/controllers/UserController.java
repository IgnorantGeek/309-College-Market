package org.campusmarket.app.controllers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UsersRepository users;


    Log log = LogFactory.getLog(UserController.class);
    
    
    static final int MAX_USER_ENTITY =100;
    
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/secured/all")
    public List<User> getAllSecure()
    {
        try
        {
            return users.findAll();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found.");
        }
    }

    @RequestMapping("/all")
    public List<User> getAll()
    {
        try
        {
            return users.findAll();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found.");
        }
    }
     
    @GetMapping("/id/{id}")
    public User findUserById(@PathVariable("id") int id)
    {
        try
        {
            return users.findById(id);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
    }
    
    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable("email") String email)
    {
        try
        {
            return users.findByEmail(email);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
    }

    @GetMapping("/username/{username}")
    public User findUserByUserName(@PathVariable("username") String username)
    {
        try
        {
            return users.findByUsername(username);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found.");
        }
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void newUser(@RequestBody final User user)
    {
        try
        {
            users.save(user);
            log.info("Data Entry Successful: New user created with ID " + user.getId());
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad JSON format.", e);
        }
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id)
    {
        try
        {
            users.deleteById(id);
            log.info("User Removal Successful: User with ID: " + id + " removed.");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not remove the user with id: " + id);
        }
    }
    
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll()
    {
        try
        {
        	int id=1;
        	while (id <= MAX_USER_ENTITY) { //assuming that this is the max num of entity. Could've used users.count but id doesn't reset at 0 for a adding a new item after deleting everything or could've written a customized query
        		users.deleteById(id);
        		id++;
        	}
        	
            log.info("User Table Cleared: all users removed.");
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users in database to remove.");
        }
    }
    
    

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User u,
                           @PathVariable("id") int id)
    {
        try
        {
            User oldUser=users.findById(id);

            oldUser.setEmail(u.getEmail());
            oldUser.setUsername(u.getUsername());
            oldUser.setFirstname(u.getFirstname());
            oldUser.setLastname(u.getLastname());
            oldUser.setPassword(u.getPassword());
            oldUser.setUniversity(u.getUniversity());

            users.save(oldUser);
                
            log.info("Data Entry Successful: User with ID " + id + " updated.");

            // Exit
            return;
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the user with id: " + id);
        }
    }
    
    @RequestMapping(value = "/toString/{id}")
    public String UserToString(@PathVariable("id") int id)
    {
        try
        {
            return users.findById(id).toString();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find the user with id: " + id);
        }
    }
}
