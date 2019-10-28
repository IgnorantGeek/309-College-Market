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

@Repository
@Component
public interface UsersRepository extends JpaRepository<User, Integer>
{

    List<User> findAll();
    
    User findById(@Param("id") int id);
    User findByEmail(@Param("email") String email);
    User findByUsername(@Param("username") String username);
    
    @Query(nativeQuery = true, value="DELETE FROM users WHERE id=:id")
    @Modifying
    void deleteById (@Param("id") int id);

    
    @Query(nativeQuery = true, value="SELECT EXISTS (SELECT * from users where username=:username)")
    @Transactional(readOnly = true)
	int existsByUserName(@Param("username") String username);
}

