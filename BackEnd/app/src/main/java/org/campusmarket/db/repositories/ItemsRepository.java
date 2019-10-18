package org.campusmarket.db.repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.campusmarket.app.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ItemsRepository extends JpaRepository<Item, Integer>
{
	
	
	 Item findByRefnum(@Param("refnum") int refnum);
	 
	@Query(nativeQuery = true, value="SELECT * FROM items WHERE name LIKE %:name% ORDER BY price")
    @Transactional(readOnly = true)
	Collection<Item>findByName(@Param("name") String name);
    
    @Query(nativeQuery = true, value="SELECT * FROM items WHERE category=:category ORDER BY price")
    @Transactional(readOnly = true)
    Collection<Item> findByCategory(@Param("category") String category);
    
    
    @Query(nativeQuery = true, value="SELECT * FROM items WHERE cond=:cond")
    @Transactional(readOnly = true)
    Collection<Item>findByCond(@Param("cond") String cond);

    @Query(nativeQuery = true, value="SELECT * FROM items WHERE name LIKE %:name% AND cond=:cond")
    @Transactional(readOnly = true)
    Collection<Item>findByCondAndName(@Param("name") String name,@Param("cond") String cond);
    
    @Query(nativeQuery = true, value="SELECT * FROM items WHERE cond=:cond AND category=:category AND price<=:price ORDER BY price")
    @Transactional(readOnly = true)
    Collection<Item>findByCondAndCategoryAndPrice(@Param("cond") String cond, @Param("category")String category, @Param("price") double price);
    
    @Query(nativeQuery = true, value="SELECT * FROM items WHERE seller=:seller")
    @Transactional(readOnly = true)
    ArrayList<Item>findBySeller(@Param("seller") String seller);

    @Query(nativeQuery = true, value="SELECT * FROM items WHERE name LIKE %:name% AND cond=:cond AND category=:category AND price<=:price")
    @Transactional(readOnly = true)
    Collection<Item>sortQuery(@Param("name") String name, @Param("cond") String cond, @Param("category")String category, @Param("price") double price);
    

	
}
