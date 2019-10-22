package org.campusmarket.app.models;

import java.util.List;

import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    UsersRepository repo;

    public List<User> findAll()
    {					
	    return repo.findAll();
    }

    public User findByEmail(String email)
    {
        return repo.findByEmail(email);
    }

    public User findById(int id)
    {
        return repo.findById(id);
    }
}