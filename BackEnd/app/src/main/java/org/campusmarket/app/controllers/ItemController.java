package org.campusmarket.app.controllers;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.ItemsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@RestController
//@RequestMapping("/items")




public class ItemController {
	@Autowired
    private ItemsRepository items;
	
	@Autowired
    private UsersRepository users;

	
	Log log = LogFactory.getLog(ItemController.class);

	
	
	@RequestMapping("/items/all")
	public List<Item> getAll(){
		try {
			
			
	    return items.findAll();
		}
		   catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items were found.");
        }
    }

	 
	@PostMapping("/users/{username}/items/new")
	public void newItem(@RequestBody Item item, @PathVariable (value = "username") String username)
	{
		try {
			
			User u=users.findByUsername(username);
			item.setUser(u);
			items.save(item);
			
        log.info(" success: a new item was created with a reference number(keep for your record): " + item.getRefnum());

		}
		   catch (Exception e){
	            log.error(e.getMessage());
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no such user with this username so sorry we won't be able to add your item :(",e);
	        }
	    }
	
	
    @PutMapping("/users/{username}/items/{refnum}")
	public void updateItem(@RequestBody Item item, @PathVariable (value = "username") String username, 
			 @PathVariable (value = "refnum") int refnum) {
    
    	try {
    	Item oldItem=items.findByRefnum(refnum);
    	oldItem.setName(item.getName());
    	oldItem.setPrice(item.getPrice());
    	oldItem.setCategory(item.getCategory());
    	oldItem.setCondition(item.getCondition());
    	items.save(oldItem);
        log.info(" success: the item with a reference number of " + refnum +" was updated");
    	}
    	  catch(Exception e)
        {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the item with refnum: " + refnum);
        }
    }
    	

    @GetMapping("name/{name}")
    public Collection<Item> findItemByName(@PathVariable("name") String name, @RequestBody Item item) {
    	
    	try {
    	return items.findByName(name);
    	
    	} catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with the name" + item.getName()+ " was found in the market");

    	}
    }
     
    @GetMapping("category/{category}")
    public Collection<Item> findItemByCategory(@PathVariable("category") String category) {
    	return items.findByCategory(category);
    }
    
    
    @GetMapping("cond/{cond}")
    public Collection<Item> findItemByCondition(@PathVariable("cond") String cond) {
    	return items.findByCond(cond);
    }
     
    @GetMapping("name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
		return items.findByCondAndName(name, cond);
    	
    }
    
    @GetMapping("category/{category}/cond/{cond}/price/{price}")
    public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price){
		return items.findByCondAndCategoryAndPrice(cond, category, price);
    	
    }
    
    
    
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll()
    {
         items.deleteAll();   
    }
}
