package org.campusmarket.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity()
@Table(name = "sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session
{
    @Id
    @Column(name = "sessid")
    private String Id;

    public Session() { }
    
    public Session(String Id)
    {
        this.Id = Id;
    }
    
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
}