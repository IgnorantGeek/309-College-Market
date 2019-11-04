package org.campusmarket.app.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



/**
 * a class to represent the controller for users
 * @author fadelalshammasi
 * @author nheisler 
 */
@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UsersRepository users;

    @Autowired
    private SessionsRepository sessions;


    Log log = LogFactory.getLog(UserController.class);
    
    
    
    /**
     * A constant to keep track of the max number of users possible in the app (subject to change)
     */
    static final int MAX_USER_ENTITY =100;
    
    
    /**
     * A method to get all users in the users table 
     * @return a list of all users in the database
     */
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

   /**
    * A method to check if a user exists given their unique usernames
    * @param username
    * @return true if it does, otherwise false
    */
    @RequestMapping("/exists/username/{username}")
    public boolean checkUsername(@PathVariable("username") String username)
    {
        if (users.existsByUserName(username) == 1) return true;
        else return false;
    }

    /**
     * A method to check if a user exists given their unique emails
     * @param email
     * @return true if it does, otherwise false
     */
    @RequestMapping("/exists/email/{email}")
    public boolean checkEmail(@PathVariable("email") String email)
    {
        User u = users.findByEmail(email);
        if (u == null) return false;
        else return true;
    }

    
    /**
     * A method to just test  if getting a user given their seessid works
     * @param sessid
     * @return the user given their seessid 
     */
    @RequestMapping("/test")
    public User testMethod(@RequestParam(name = "sessid", required = true) String sessid)
    {
        return users.findById(sessions.findUserBySession(sessid));
    }
     
    /**
     *  A method to get a user given his/her id
     * @param id
     * @param sessid
     * @return the user that has that id
     */
    @GetMapping("/id/{id}")
    public User findUserById(@PathVariable("id") int id, @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

        User loggedIn = users.findById(sessions.findUserBySession(sessid));

        User find = users.findById(id);

        if (find == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with ID: " + id);

        if (active.getAdmin() || loggedIn.compareTo(find))
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
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
    
    /**
     *  A method to get a user given his/her email
     * @param id
     * @param sessid
     * @return the user that has that email
     */
    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable("email") String email, @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
        
        User loggedIn = users.findById(sessions.findUserBySession(sessid));

        User find = users.findByEmail(email);

        if (find == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with email: " + email);

        if (active.getAdmin() || loggedIn.compareTo(find))
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
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
    
    /**
     *  A method to get a user given his/her username
     * @param id
     * @param sessid
     * @return the user that has that username
     */
    @GetMapping("/username/{username}")
    public User findUserByUserName(@PathVariable("username") String username, @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
        
        User loggedIn = users.findById(sessions.findUserBySession(sessid));

        User find = users.findByUsername(username);

        if (find == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with ID: " + username);

        if (active.getAdmin() || loggedIn.compareTo(find))
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
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
    
    /**
     * A method to post a new user into the database 
     * @param user
     */
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
    
    /**
     * A method to delete a user from the database given his/her ID
     * @param id
     * @param sessid
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id, @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

        if (active.getAdmin() || sessions.findUserBySession(sessid) == id)
        {
         //   try
          //  {
               users.deleteById(id);
           //     log.info("User Removal Successful: User with ID: " + id + " removed.");
          //  }
         //   catch (Exception e)
          //  {
             //   log.error(e.getMessage());
             //   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not remove the user with id: " + id);
           // }
       // }
       // else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
}  
    /**
     * A method to clean up  and delete all the users from the database 
     * @param sessid
     */
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll(@RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

        if (active.getAdmin())
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
    }
    
    
/**
 * A method to update the all fields for a specific user in the users table
 * @param u
 * @param id
 * @param sessid
 */
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

        if (active.getAdmin() || sessions.findUserBySession(sessid) == id)
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
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
    
    /**
     * A method to get the string representation of a user given the id
     * @param id
     * @return string representation of the user
     */
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
