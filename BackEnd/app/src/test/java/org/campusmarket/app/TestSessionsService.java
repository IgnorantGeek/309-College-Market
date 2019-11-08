package org.campusmarket.app;

import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.SessionService;
import org.campusmarket.db.repositories.SessionsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestSessionsService
{
    @InjectMocks
    SessionService SessionService;

    @Mock
    SessionsRepository repo;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestNewSession()
    {
        Session sess;
    }
}