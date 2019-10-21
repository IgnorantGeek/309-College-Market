package org.campusmarket.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity()
@Table(name = "sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session
{
    @Id
    @Column(name = "sessid")
    private String sessId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    /*---Constructors---*/
    public Session() { }
    
    public Session(String Id, User user)
    {
        this.sessId = Id;
        this.user = user;
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
    /**
     * @return the user
     */
    public User getUser() 
    {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(User user)
    {
        this.user = user;
    }
}