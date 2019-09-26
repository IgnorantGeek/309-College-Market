package org.campusmarket.db.repositories;

import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface ItemsRepository extends JpaRepository<Item, Integer>
{

    Item findByName(@Param("name") String name);
    Item findByCategory(@Param("category") String category);
    Item findByCond(@Param("cond") String cond);

    

	
}
