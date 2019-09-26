package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User
{
    /*--- Class Variables ---*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int     Id;
    @Column(name = "username")
    private String  username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String  firstname;
    @Column(name = "lastname")
    private String  lastname;
    @Column(name = "email")
    private String  email;
    @Column(name = "university")
    private String  university;

    @OneToMany(cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Session> sessions = new ArrayList<Session>();
    
    /*--- Constructors ---*/
    public User() { }
    // Do we need more constructors?

    /*--- Getters/Setters ---*/
    public String getUsername()
    {
        return this.username;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getFirstname()
    {
        return this.firstname;
    }
    public String getLastname()
    {
        return this.lastname;
    }
    public int getId()
    {
        return this.Id;
    }
    public String getUniversity()
    {
        return this.university;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setUsername(String userName)
    {
        this.username = userName;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setFirstname(String firstName)
    {
        this.firstname = firstName;
    }
    public void setLastname(String lastName)
    {
        this.lastname = lastName;
    }
    public void setId(int Id)
    {
        this.Id = Id;
    }
    public void setUniversity(String university) 
    {
        this.university = university;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    /*--- Class Methods ---*/
    @Override
    public String toString()
    {
        String ret = new String();
        // Convert the class info into a string format
        ret = String.format("{Username:%1$s}\n{Id:%2$d}\n{Firstname:%3$s}\n{Lastname:%4$s}\n{Email:%5$s}\n{University:%6$s}\n\n",
                            this.username,
                            this.Id,
                            this.firstname,
                            this.lastname,
                            this.email,
                            this.university
                            );
        return ret;
    }
}