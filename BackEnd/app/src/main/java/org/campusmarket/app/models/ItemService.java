package org.campusmarket.app.models;
import java.util.ArrayList;
import java.util.List;

import org.campusmarket.db.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


@Service
public class ItemService {

	@Autowired
	 private ItemsRepository repo;
	

	public List<Item> getAllItemList(){
						
	    return repo.findAll();
	
	}
	
	public Item findByRefnum(int refnum) {
		return repo.findByRefnum(refnum);
		
	}
	
	public ArrayList<Item>findBySeller( @Param("seller") String seller){
		 return repo.findBySeller(seller);
	}
	
	
	
	
}