package org.campusmarket.app.models;

import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * A service class to help testing the mockito for users
 * @author nehisler
 *
 */
@Service
public class SessionService
{
    @Autowired
    SessionsRepository repo;

    @Autowired
    UsersRepository users;
}