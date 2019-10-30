package org.campusmarket.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.campusmarket.app.models.LoginRequest;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;

import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/sessions")
public class SessionController
{
    @Autowired
    private UsersRepository users;

    @Autowired
    private SessionsRepository sessions;

    Log log = LogFactory.getLog(SessionController.class);
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newSession(@RequestBody final LoginRequest req)
    {
        User find = users.findByUsername(req.getUsername());
        // Check for errors
        if (find == null)
        {
            log.error("Login Failed: No User found with Username " + req.getUsername());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login Error: username or password incorrect.");
        }
        else if (!find.getPassword().equals(req.getPassword()))
        {
            log.error("Login Failed: Incorrect password for User " + find.getUsername() + ":" + find.getId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login Error: username or password incorrect.");
        }
        else
        {
            int lowerlet  = 65;
            int upperlet  = 90;
            int lowernum  = 48;
            int uppernum  = 57;
            int sessidlen = 16;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(sessidlen);
            for (int i = 0; i < sessidlen; i++) 
            {
                int rand = random.nextInt();
                int randomLimitedInt;
                if (rand % 2 == 0)
                {
                    randomLimitedInt = random.nextInt(upperlet-lowerlet) + lowerlet;
                }
                else
                {
                    randomLimitedInt = random.nextInt(uppernum-lowernum) + lowernum;
                }
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            final Session sess = new Session(generatedString, find.getAdmin());
            find.addSession(sess);
            users.save(find);
            
            log.info("New session with ID " + generatedString + " assigned to User " + find.getUsername() + ":" + find.getId());

            return generatedString;
        }
    }

    // Only for admins for now
    @RequestMapping(value = "/sessid/{sess_id}", method = RequestMethod.GET)
    public Session findById(@PathVariable("sess_id") String getId, @RequestParam(name = "sessid", required = true) String sessid)
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
                return sessions.findBySessId(getId);
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No session found with ID: " + getId);
            }
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This function is restricted to admin users, and the user in question.");
    }

    // Only for admins
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Session> getAll(@RequestParam(name = "sessid", required = true) String sessid)
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
                return sessions.findAll();
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sessions found.");
            }
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This function is limited to admin users only. Please log in with an admin account.");
    }

    @RequestMapping(value = "/close/{sessid}", method = RequestMethod.DELETE)
    public void closeSession(@PathVariable("sessid") String close_id, @RequestParam(name = "sessid", required = true) String sessid)
    {
        if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        Session close = sessions.findBySessId(close_id);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
        
        if (active.getAdmin() || sessid == close_id)
        {
            try
            {
                User u = users.findById(sessions.findUserBySession(close_id));
                u.dropSession(close);
                log.info("Session with ID " + close_id + " closed.");
                sessions.deleteById(close_id);
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found.");
            }
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This function is restricted to admin users, and the user in question.");
    }

    // @RequestMapping(value = "/close/all/user/{userid}", method = RequestMethod.DELETE)
    // public void closeAllByUser(@PathVariable("userid") int userid, @RequestParam(name = "sessid", required = true) String sessid)
    // {
    //     if (sessid.isEmpty())
    //     {
    //         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
    //     }

    //     Session active = sessions.findBySessId(sessid);
        
    //     if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

    //     if (active.getAdmin())
    //     {
    //         try
    //         {
    //             ArrayList<Session> u_sess = sessions.findAllByUserId(userid);
    //             for (int i = 0; i < u_sess.size(); i++)
    //             {
    //                 sessions.delete(u_sess.get(i));
    //             }
    //         }
    //         catch (Exception e)
    //         {
    //             throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    //         }
    //     }
    // }

    @RequestMapping(value = "/userid/{userid}", method = RequestMethod.GET)
    public ArrayList<Session> getAllByUser(@PathVariable("userid") int id, @RequestParam(name = "sessid", required = true) String sessid)
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
                return sessions.findAllByUserId(id);
            }
            catch (Exception e)
            {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No active sessions found for user with id: " + id);
            }
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This function is restricted to admin users, and the user in question.");
    }
}