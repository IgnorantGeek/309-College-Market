package org.campusmarket.app.controllers;

import org.campusmarket.app.models.LoginRequest;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;

import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/sessions")
public class SessionController
{
    @Autowired
    private UsersRepository users;

    @Autowired
    private SessionsRepository sessions;
    
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newSession(@RequestBody final LoginRequest req)
    {
        User find = users.findByUsername(req.getUsername());
        if (find == null)
        {
            return "Logon Error: username or password incorrect.";
        }
        else if (!find.getPassword().equals(req.getPassword()))
        {
            return "Logon Error: username or password incorrect.";
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
            final Session sess = new Session(generatedString);
            sessions.save(sess);
            return generatedString;
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Session> getAll()
    {
        return sessions.findAll();
    }
}