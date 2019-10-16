package org.campusmarket.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UsersRepository users;

    @Autowired
    private SessionsRepository sessions;


    Log log = LogFactory.getLog(UserController.class);
    
    
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
            log.info("User Removal Successful: User with ID " + id + " removed.");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found.", e);
        }
    }
    
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll(@RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

        if (active.getUser().getAdmin())
        {
            try
            {
                users.deleteAll();
                log.info("User Table Cleared: all users removed.");
            }
            catch(Exception e)
            {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No users in database to remove.");
            }
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User u,
                           @PathVariable("id") int id,
                           @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
        
        if (active.getUser() == users.findById(id)
         || active.getUser().getAdmin())
        {
            try
            {
                // this method of updating the user doesn't actually work. doesn't send the result to the server
                User oldUser=users.findById(id);

                oldUser.setEmail(u.getEmail());
                oldUser.setUsername(u.getUsername());
                oldUser.setFirstname(u.getFirstname());
                oldUser.setLastname(u.getLastname());
                oldUser.setPassword(u.getPassword());
                oldUser.setUniversity(u.getUniversity());

                // need something here to send the change to SQL
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
    }
    @RequestMapping(value = "/toString/{id}")
    public String PrintUserId(@PathVariable("id") int id)
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