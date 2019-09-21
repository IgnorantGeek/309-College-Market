package org.campusmarket.db.repositories;

import org.campusmarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends JpaRepository<User, Integer>
{

    List<User> findAll();
    
    User findById(@Param("id") int id);
    User findByEmail(@Param("email") String email);
    User findByUsername(@Param("username") String username);
    
    User save(User owner);


	
}
