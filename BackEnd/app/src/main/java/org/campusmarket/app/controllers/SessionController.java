package org.campusmarket.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.campusmarket.app.models.LoginRequest;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;

import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;

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
            return "Login Error: username or password incorrect.";
        }
        else if (!find.getPassword().equals(req.getPassword()))
        {
            log.error("Login Failed: Incorrect password for User " + find.getUsername() + ":" + find.getId());
            return "Login Error: username or password incorrect.";
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
            final Session sess = new Session(generatedString, find);
            sessions.save(sess);
            
            log.info("New session with ID " + generatedString + " assigned to User " + find.getUsername() + ":" + find.getId());

            return generatedString;
        }
    }

    @RequestMapping(value = "/sessid/{sessid}", method = RequestMethod.GET)
    public Session findById(@PathVariable("sessid") String sessid)
    {
        try
        {
            return sessions.findBySessId(sessid);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No session found.");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Session> getAll()
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

    @RequestMapping(value = "/close/{sessID}", method = RequestMethod.GET)
    public void closeSession(@PathVariable("sessID") String sessID)
    {
        try
        {
            log.info("Session with ID " + sessID + " closed.");
            sessions.findById(sessID);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Session not found.");
        }
    }
}