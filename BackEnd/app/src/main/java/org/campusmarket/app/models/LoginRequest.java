package org.campusmarket.app.models;

public class LoginRequest
{
    private String username;
    private String password;

    public LoginRequest() { }
    public LoginRequest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    
    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }
    /**
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }
}