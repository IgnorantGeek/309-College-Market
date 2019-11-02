package org.campusmarket.app.models;
import java.util.ArrayList;
import java.util.List;

import org.campusmarket.db.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * A service class to help testing the mockito for items 
 * @author fadelalshammasi
 *
 */
@Service
public class ItemService {

	@Autowired
	 private ItemsRepository repo;
	

	/**
	 * A method to get all items 
	 * @return the list of all items added 
	 */
	public List<Item> getAllItemList(){
						
	    return repo.findAll();
	
	}
	
	/**
	 * A method to find an item given their refnum 
	 * @param refnum
	 * @return the item that has that unique refnum 
	 */
	public Item findByRefnum(int refnum) {
		return repo.findByRefnum(refnum);
		
	}
	
	/**
	 * A method to get all items sold by a specfic seller 
	 * @param seller
	 * @return an arraylist of items what were sold by seller 
	 */
	public ArrayList<Item>findBySeller( @Param("seller") String seller){
		 return repo.findBySeller(seller);
	}
	
	
	
	
}