package org.campusmarket.app.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity()
@Table(name = "sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session
{
    @Id
    @Column(name = "sessid")
    private String Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;


    /*---Constructors---*/
    public Session() { }
    
    public Session(String Id, User user)
    {
        this.Id = Id;
        this.user = user;
    }


    /*---Getters and Setters--*/
    
    /**
     * @return the id
     */
    public String getId()
    {
        return Id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        Id = id;
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