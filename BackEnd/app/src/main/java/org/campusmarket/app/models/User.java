package org.campusmarket.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "first_name")
    private String  first_name;
    @Column(name = "last_name")
    private String  last_name;
    @Column(name = "email")
    private String  email;
    @Column(name = "university")
    private String  university;
    
    /*--- Constructors ---*/
    public User() { }
    // Do we need more constructors?

    /*--- Getters/Setters ---*/
    public String getUser_name()
    {
        return this.username;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getFirst_name()
    {
        return this.first_name;
    }
    public String getLast_name()
    {
        return this.last_name;
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
    public void setUser_name(String userName)
    {
        this.username = userName;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setFirst_name(String firstName)
    {
        this.first_name = firstName;
    }
    public void setLast_name(String lastName)
    {
        this.last_name = lastName;
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
                            this.first_name,
                            this.last_name,
                            this.email,
                            this.university
                            );
        return ret;
    }
}