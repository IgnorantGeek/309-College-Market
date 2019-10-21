package org.campusmarket.app.models;
import java.util.ArrayList;
import java.util.List;

import org.campusmarket.db.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ItemService {

	@Autowired
	 private ItemsRepository repo;
	
//more to come

	public List<Item> getAllItemList(){
						
	    return repo.findAll();
	
	}
	
	public ArrayList<Item>findBySeller( @Param("seller") String seller){
		 return repo.findBySeller(seller);
	}
	
	
	
	
}