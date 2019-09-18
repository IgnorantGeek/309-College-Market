package org.collegemarket.db.repositories;

import java.util.Collection;
//import org.hibernate.mapping.Collection;
import org.collegemarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository

public interface UsersRepository extends JpaRepository<User, Integer>
{
	Collection <User> finaAll();
	
	//finding by a specific column 
	Collection <User>findById(@Param("id") int id);
	Collection<User>findByUser(@Param ("user")String user );
	Collection <User>findByFirst_name(@Param ("first_name")String first_name );
	Collection <User>findByLast_name(@Param("last_name")String last_name);
	
	//deleting 
	Collection <User>deleteById(@Param("id") int id);
	Collection<User>deleteByUser(@Param ("user")String user );
	Collection <User>deleteByFirst_name(@Param ("first_name")String first_name );
	Collection<User> deleteByLast_name(@Param("last_name")String last_name);
	

	User save(User user); // used for POST request
	
}