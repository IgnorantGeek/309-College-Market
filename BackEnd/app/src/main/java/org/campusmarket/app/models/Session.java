package org.campusmarket.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "session")
public class Session
{
    @Id
    @Column(name = "sessid")
    private String sessId;

    @Column(name = "admin")
    private boolean admin;


    /*---Constructors---*/
    public Session() { }
    
    public Session(String Id, User user)
    {
        this.sessId = Id;
    }


    /*---Getters and Setters--*/
    
    /**
     * @return the id
     */
    public String getId()
    {
        return sessId;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        sessId = id;
    }
    public boolean getAdmin()
    {
        return this.admin;
    }
    public void setAdmin(boolean admin)
    {
        this.admin = admin;
    }
}