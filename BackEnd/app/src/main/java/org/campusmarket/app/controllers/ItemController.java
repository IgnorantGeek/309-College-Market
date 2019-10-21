
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@RestController
public class ItemController
{
	@Autowired
    private ItemsRepository items;
	
	@Autowired
	private UsersRepository users;

	Log log = LogFactory.getLog(ItemController.class);

	// green
	@RequestMapping(value = "/items/all", method = RequestMethod.GET)
	public List<Item> getAll()
	{  
		try 
		{
			return items.findAll();
		}
		catch (Exception e)
		{
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items were found.");
        }
    }

	// purple
	@RequestMapping(value = "users/{username}/items/new", method = RequestMethod.POST)
	public void newItem(@RequestBody Item item, @PathVariable("username") String username)
	{
		try
		{
			User n = users.findByUsername(username);
			item.setUser(n);
			items.save(item);
			
        	log.info(" success: a new item was created with a reference number(keep for your record): " + item.getRefnum());
		}
		catch (Exception e)
		{
	        log.error(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not add item to the database.");
	    }
	}
	// Blue
    @RequestMapping(value = "uses/{username}/items/update/{refnum}", method = RequestMethod.PUT)
	public void updateItem(@RequestBody Item item,
							@PathVariable (value = "refnum") int refnum)
	{	
		Item update = items.findByRefnum(refnum);

		if (update == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find and item with refnum: " +  refnum);
			
		try 
		{
			update.setName(item.getName());
			update.setPrice(item.getPrice());
			update.setCategory(item.getCategory());
			update.setCondition(item.getCondition());
			items.save(update);
				
			log.info(" success: the item with a reference number of " + refnum +" was updated");
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, " Failure: Could not update the item with refnum: " + refnum);
		}
    }
    
    
    // Blue
    @RequestMapping(value = "users/{username}/items/delete/{refnum}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable (value = "refnum") int refnum)
    {
		Item update = items.findByRefnum(refnum);

		if (update == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find and item with refnum: " +  refnum);
		try 
		{
			items.deleteById(refnum);
			log.info(" success: the item with a reference number of " + refnum +" was deleted");		
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item with refnum " + refnum + " not found.");
		}
    }
	
	// Green
    @RequestMapping(value = "users/{username}/items/sellers", method = RequestMethod.GET)
	public ArrayList<Item> findItemBySeller(@PathVariable("username") String seller) 
	{
		try 
		{
    		return items.findBySeller(seller);
		} 
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cound not extract seller items.");
    	}
	}
           
    @RequestMapping(value = "/items/name/{name}", method = RequestMethod.GET)
	public Collection<Item> findItemByName(@PathVariable("name") String name)
	{
		try 
		{
			return items.findByName(name);
		}
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name was found.");
    	}
    }
     
    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
	public Collection<Item> findItemByCategory(@PathVariable("category") String category)
	{
		try 
		{
    		return items.findByCategory(category);
		}
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this category were found.");
    	}
    }
    
    
    
    @GetMapping("/items/cond/{cond}")
	public Collection<Item> findItemByCondition(@PathVariable("cond") String cond)
	{
		try 
		{
			return items.findByCond(cond);
		}
		catch(Exception e)
		{
 			log.error(e.getMessage());
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this condition were found.");
 		}
 	}
 
    
     
    @GetMapping("/items/name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
		try
		{
			return items.findByCondAndName(name, cond);
		}
		catch(Exception e)
		{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name & condition were found.");

    	}
    }
    
    @GetMapping("/items/category/{category}/cond/{cond}/price/{price}")
	public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price)
	{
		try 
		{
		return items.findByCondAndCategoryAndPrice(cond, category, price);
		}
		catch (Exception e)
		{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name & category & condition & price limit were found in the cymarket");

    	}
    }
}
