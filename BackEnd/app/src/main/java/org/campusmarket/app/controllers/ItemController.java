
package org.campusmarket.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.ItemsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
    		
    		if(users.existsByUserName(username)==0) {
    			 
    			log.error("There's no such user with this username in our system so sorry we can't update this item");
    			return;

    		}
    		  		
    	
    	Item oldItem=items.findByRefnum(refnum);
    	
    	if(oldItem.getUser().getUsername().equals(username)){
    	
    	oldItem.setName(item.getName());
    	oldItem.setPrice(item.getPrice());
    	oldItem.setCategory(item.getCategory());
    	oldItem.setCondition(item.getCondition());
    	items.save(oldItem);
    	
        log.info(" success: the item with a reference number of " + refnum +" was updated");
        return;
    	}
    	
    	log.error("Unathorized attempt: Could not update the item(you don't own the item) with refnum: " + refnum);
    	
    	} catch (Exception e) {
    		 log.error(e.getMessage());
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Failure: Could not update the item with refnum: " + refnum);
    		
        }

    }
    
    
    
    @RequestMapping(value = "/users/{username}/items/{refnum}/delete", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable (value = "username") String username, 
			 @PathVariable (value = "refnum") int refnum)
    {
    	
        try {
        	if(users.existsByUserName(username)==0) {
   			 
    			log.error("Unathorized attempt: There's no such user with this username in our system so sorry we can't delete this item");
    			return;

    		}
        	
        	Item oldItem=items.findByRefnum(refnum);
        	if(oldItem.getUser().getUsername().equals(username)){
        		
        		items.deleteById(refnum);
                log.info(" success: the item with a reference number of " + refnum +" was deleted");
                return;
        	}
        	log.error("Unathorized attempt: Could not delete the item(you don't own the item) with refnum: " + refnum);
        	
        }
        catch (Exception e)
        {
            log.error("item with refnum"+ refnum + " not found" );
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item with refnum " + refnum + " not found.", e);
        }
    }
    
    @GetMapping("/users/{username}/items/sellers")
    public ArrayList<Item> findItemBySeller(@PathVariable("username") String seller) {
    	
    	try {
    	
    	return items.findBySeller(seller);
    	
    	} catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this seller was found in the cymarket");

    	}
    }
     
           
    @GetMapping("/items/name/{name}")
    public Collection<Item> findItemByName(@PathVariable("name") String name) {
    	
    	try {
    	return items.findByName(name);
    	
    	} catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name was found in the cymarket");

    	}
    }
     
    @GetMapping("/items/category/{category}")
    public Collection<Item> findItemByCategory(@PathVariable("category") String category) {
    	try {
    	return items.findByCategory(category);
    	
    	 } catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this category was found in the cymarket");

    	}
    }
    
    
    
    @GetMapping("/items/cond/{cond}")
    public Collection<Item> findItemByCondition(@PathVariable("cond") String cond) {
    	try {
    	return items.findByCond(cond);
    	
	 } catch(Exception e) {
 		log.error(e.getMessage());
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this condition was found in the cymarket");

 	}
 }
 
    
     
    @GetMapping("/items/name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
    	try {
		return items.findByCondAndName(name, cond);
		
    	}catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & condition was found in the cymarket");

    	}
    }
    
    @GetMapping("/items/category/{category}/cond/{cond}/price/{price}")
    public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price){
    	try {
		return items.findByCondAndCategoryAndPrice(cond, category, price);
    	} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & category & condition & price limit was found in the cymarket");

    	}
    }
    
    @RequestMapping(value = "/items/delete/all", method = RequestMethod.DELETE)
    public void deleteAll()
    {
    	try {
    	
         items.deleteAll(); 
         log.info("User Table Cleared: all users removed.");

    } catch (Exception e) {
    	log.error("No items in database to remove.");
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No items in database to remove.");
    }
   }
}
