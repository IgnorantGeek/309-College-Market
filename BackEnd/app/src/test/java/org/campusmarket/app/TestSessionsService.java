package org.campusmarket.app;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.when;

import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;
import org.campusmarket.app.models.services.SessionService;
import org.campusmarket.db.repositories.SessionsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestSessionsService extends TestServices
{
    @InjectMocks
    SessionService sessionService;

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
        User sellerOne=new User ("nheisler", "abc123","Nick","Heisler","nheisler@iastate.edu","isu", false);
        Session sess = new Session("123456789abcdefg", false);

        System.out.println("Session created with " + sess.getId());

        repo.save(sess);
        if (sellerOne.getSessions() == null) System.out.println("Bad news");
        if (sess != null && sellerOne != null) sellerOne.addSession(sess);
        Session s = new Session();

        for (Iterator<Session> it = sellerOne.getSessions().iterator(); it.hasNext();)
        {
            s = it.next();
            if (sess.equals(s)) break;
        }

        assertEquals(s, sess);
    }

    @Test
    public void TestGetSessions()
    {
        User sellerOne=new User ("nheisler", "abc123","Nick","Heisler","nheisler@iastate.edu","isu", false);
        sellerOne.setId(3);
        Session sess1 = new Session("123456789abcdefg", false);
        Session sess2 = new Session("Abcdefg123456789", true);
        Session sess3 = new Session("ABCDEFG123456789", true);
        ArrayList<Session> list = new ArrayList<Session>();
        sellerOne.addSession(sess1);
        sellerOne.addSession(sess2);
        sellerOne.addSession(sess3);

        list.add(sess1);
        list.add(sess2);
        list.add(sess3);

        when (super.SessionsRepo.findAllByUserId(sellerOne.getId())).thenReturn(list);

        List<Session> return_list = sessionService.findAllByUserId(sellerOne.getId());

        assertEquals(3, return_list.size());
    };
}