package org.campusmarket.db.repositories;

import org.campusmarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * An interface for User to represent the UsersRepository. It that has the declarations of the methods that we use in the controller with some customized queries 
 * @author fadelalshammasi
 * @author nheisler 
 *
 */
@Repository
@Component
public interface UsersRepository extends JpaRepository<User, Integer>
{

	/**
     * A method to get all users in the users table 
     * @return a list of all users in the database
     */
    List<User> findAll();
    
    /**
     * find a user by their ID
     * @param id
     * @return the user with id
     */
    User findById(@Param("id") int id);
    
    /**
     * find a user by their email
     * @param email
     * @return the user with email
     */
    User findByEmail(@Param("email") String email);
    
    
    /**
     * find a user by their username
     * @param username
     * @return the user with username
     */
    User findByUsername(@Param("username") String username);
    
    /**
     * A customized query to help deleting a user from the users table given their id
     * @param id
     */
    @Query(nativeQuery = true, value="DELETE FROM users WHERE id=:id")
    @Modifying
    void deleteById (@Param("id") int id);

    
    /**
     * A method to check if a user exists in the users table or not given their username 
     * @param username
     * @return 1 if it does, otherwise 0 
     */
    @Query(nativeQuery = true, value="SELECT EXISTS (SELECT * from users where username=:username)")
    @Transactional(readOnly = true)
	int existsByUserName(@Param("username") String username);
}

