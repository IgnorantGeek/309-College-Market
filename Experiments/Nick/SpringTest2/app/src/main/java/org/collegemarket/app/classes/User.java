package org.collegemarket.app.classes;

public class User
{
    /*--- Class Variables ---*/
    private String userName;
    private String firstName;
    private String lastName;
    private int Id;   // Where should we get the ID?
    
    /*--- Constructors ---*/
    public User(String username)
    {
        this.userName = username;
        this.Id = -1;
    }
    public User(String username, String firstName, String lastName)
    {
        this.userName = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.Id = -1;
    }

    /*--- Getters/Setters ---*/
    public String getuserName()
    {
        return this.userName;
    }
    public String getfirstName()
    {
        return this.firstName;
    }
    public String getlastName()
    {
        return this.lastName;
    }
    public int getId()
    {
        return this.Id;
    }
    public void setuserName(String userName)
    {
        this.userName = userName;
    }
    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }
    public void setId(int Id)
    {
        this.Id = Id;
    }

    /*--- Class Methods ---*/
    @Override
    public String toString()
    {
        String ret = new String();
        // Convert the class info into a string format
        return ret;
    }
}