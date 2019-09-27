package org.campusmarket.db.repositories;

import java.util.Collection;

import org.campusmarket.app.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ItemsRepository extends JpaRepository<Item, Integer>
{
	
	@Query(nativeQuery = true, value="SELECT * from items WHERE name LIKE %:name%")
    @Transactional(readOnly = true)
	Collection<Item>findByName(@Param("name") String name);
    
    @Query(nativeQuery = true, value="SELECT * from items WHERE category=:category")
    @Transactional(readOnly = true)
    Collection<Item> findByCategory(@Param("category") String category);
    
    
    @Query(nativeQuery = true, value="SELECT * from items WHERE cond=:cond")
    @Transactional(readOnly = true)
    Collection<Item>findByCond(@Param("cond") String cond);

    @Query(nativeQuery = true, value="SELECT * from items WHERE name LIKE %:name% AND cond=:cond")
    @Transactional(readOnly = true)
    Collection<Item>findByCondAndName(@Param("name") String name,@Param("cond") String cond);


	
}
