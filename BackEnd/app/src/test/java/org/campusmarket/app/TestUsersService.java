package org.campusmarket.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.campusmarket.app.models.User;
import org.campusmarket.app.models.UserService;
import org.campusmarket.db.repositories.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestUsersService
{
    @InjectMocks
    UserService userService;

    @Mock
    UsersRepository repo;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test // test 1 NH
    public void getUserByEmailTest()
    {
        when(repo.findByEmail("nheisler@iastate.edu")).thenReturn(new User("nheisler",
                                                                           "testpw",
                                                                           "Nick",
                                                                           "Heisler",
                                                                           "nheisler@iastate.edu",
                                                                           "Iowa State University",
                                                                           true));

        User test = userService.findByEmail("nheisler@iastate.edu");
        
        assertEquals("nheisler", test.getUsername());
        assertEquals("testpw", test.getPassword());
        assertEquals("Nick", test.getFirstname());
        assertEquals("Heisler", test.getLastname());
        assertEquals("nheisler@iastate.edu", test.getEmail());
        assertEquals("Iowa State University", test.getUniversity());
        assertTrue(test.getAdmin());
    }

    @Test // test 2 NH
    public void GetAllUserTest()
    {
        List<User> list = new ArrayList<User>();

        User first = new User("nheisler","testpw","Nick","Heisler","nheisler@iastate.edu","Iowa State University",true);
        User second = new User("nheisler1","testpw","Nick","Heisler","nheisler1@iastate.edu","Iowa State University",false);
        User third = new User("nheisler2","testpw","Nick","Heisler","nheisler2@iastate.edu","Iowa State University",true);

        list.add(first);
        list.add(second);
        list.add(third);

        when(repo.findAll()).thenReturn(list);

        List<User> testList = userService.findAll();

        assertEquals(3, testList.size());

        assertFalse(testList.get(1).getAdmin());
    }

    @Test // test 3 NH
    public void GetUserByIdTest()
    {
        when(repo.findById(1)).thenReturn(new User("nheisler",
                                                   "testpw",
                                                   "Nick",
                                                   "Heisler",
                                                   "nheisler@iastate.edu",
                                                   "Iowa State University",
                                                   true));

        User test = userService.findById(1);

        assertEquals("nheisler", test.getUsername());
        assertEquals("testpw", test.getPassword());
        assertEquals("Nick", test.getFirstname());
        assertEquals("Heisler", test.getLastname());
        assertEquals("nheisler@iastate.edu", test.getEmail());
        assertEquals("Iowa State University", test.getUniversity());
        assertTrue(test.getAdmin());
    }
}