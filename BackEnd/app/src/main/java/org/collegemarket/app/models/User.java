package org.collegemarket.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
    /*--- Class Variables ---*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int     Id;
    @Column(name = "user_name")
    private String  user_name;
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
    public String getuserName()
    {
        return this.user_name;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getfirstName()
    {
        return this.first_name;
    }
    public String getlastName()
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
    public void setuserName(String userName)
    {
        this.user_name = userName;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public void setfirstName(String firstName)
    {
        this.first_name = firstName;
    }
    public void setlastName(String lastName)
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
        ret = String.format("{Username:%1$s}\n{Id:%2$d}\n{Firstname:%3$s}\n{Lastname:%4$s}",
                            this.user_name,
                            this.Id,
                            this.first_name,
                            this.last_name);
        return ret;
    }
}