package org.campusmarket.app.models;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    /*--- Class Variables ---*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int     Id;
    
    @NotNull
    @Column(name = "username", unique = true)
    private String  username;
    
    
    @NotNull
    @Column(name = "password")
    private String password;
    
    @Column(name = "firstname")
    private String  firstname;
    
    @Column(name = "lastname")
    private String  lastname;
    
    @Column(name = "email", unique = true)
    private String  email;
    
    @Column(name = "university")
    private String  university;
    
    @Column(name = "admin")
    private boolean admin;
    

    /*--- Links to Other Repositories ---*/
    @OneToMany(cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JoinTable(name = "user_sessions", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "sess_id"))
    @JsonIgnore()
    private Set<Session> sessions;

    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    // private List <Item> items;

    
    /*--- Constructors ---*/
    public User() { }

    public User(String username, 
                String password,
                String firstname,
                String lastname,
                String email,
                String university,
                boolean admin)
    {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.university = university;
        this.admin = admin;
    }
    

    /*--- Getter Methods ---*/
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
    public boolean getAdmin()
    {
        return this.admin;
    }
    public Set<Session> getSessions()
    {
        return sessions;
    }

    /*--- Setter Methods ---*/
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
    public void setSessions(Set<Session> sessions)
    {
        this.sessions = sessions;
    }


    /*--- Class Methods ---*/
    @Override
    public String toString()
    {
        String ret = new String();
        // Convert the class info into a string format
        ret = String.format("{Username:%1$s}\n{Id:%2$d}\n{Firstname:%3$s}\n{Lastname:%4$s}\n{Email:%5$s}\n{University:%6$s}\n{Admin:%7$b}",
                            this.username,
                            this.Id,
                            this.firstname,
                            this.lastname,
                            this.email,
                            this.university,
                            this.admin
                            );
        return ret;
    }

    public void addSession(Session s)
    {
        this.sessions.add(s);
    }

    public void dropSession(Session s)
    {
        this.sessions.remove(s);
    }
}
