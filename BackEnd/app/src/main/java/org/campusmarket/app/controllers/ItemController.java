
package org.campusmarket.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.ItemsRepository;
import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@RestController
@RequestMapping("/items")
public class ItemController
{
	@Autowired
    private ItemsRepository items;
	
	@Autowired
	private UsersRepository users;
	
	@Autowired
	private SessionsRepository sessions;

	Log log = LogFactory.getLog(ItemController.class);

	// green
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Item> getAll(@RequestParam(name = "sessid", required = true) String sessid)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
         
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
	@RequestMapping(value = "user/{username}/new", method = RequestMethod.POST)
	public void newItem(@RequestBody Item item, @PathVariable("username") String username, @RequestParam(name = "sessid", required = true) String sessid)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
		}
		Session active = sessions.findBySessId(sessid);
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
		
		try
		{
			User n = active.getUser();
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
    @RequestMapping(value = "/update/{refnum}", method = RequestMethod.PUT)
	public void updateItem(@RequestBody Item item,
						   @RequestParam(name = "sessid", required = true) String sessid, 
							@PathVariable (value = "refnum") int refnum)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
		
		Item update = items.findByRefnum(refnum);

		if (update == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find and item with refnum: " +  refnum);
		
		if (active.getUser().getAdmin()
			|| active.getUser() == update.getUser())
		{
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
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to update this item.");
    }
    
    
    // Blue
    @RequestMapping(value = "/delete/{refnum}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable (value = "refnum") int refnum, @RequestParam(name = "sessid", required = true) String sessid)
    {
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
		
		Item update = items.findByRefnum(refnum);

		if (update == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find and item with refnum: " +  refnum);
	
		if (active.getUser().getAdmin()
		 || active.getUser() == update.getUser())
		{
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
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to delete this item.");
    }
	
	// Green
    @RequestMapping(value = "/sellers/{username}", method = RequestMethod.GET)
	public ArrayList<Item> findItemBySeller(@PathVariable("username") String seller, @RequestParam(name = "sessid", required = true) String sessid) 
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

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
	
	// Blue
	@RequestMapping(value = "/forsale", method = RequestMethod.GET)
	public ArrayList<Item> findMyForSaleItems(@RequestParam(name = "sessid", required = true) String sessid)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		try 
		{
    		return items.findBySeller(active.getUser().getUsername());
		} 
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cound not extract for this users for-sale items.");
    	}
	}
           
    @RequestMapping(value = "/sortby", method = RequestMethod.GET)
	public Collection<Item> findItemByName(@RequestParam(name = "sessid", required = true) String sessid,
										   @RequestParam(name = "name") String name,
										   @RequestParam(name = "category") String category,
										   @RequestParam(name = "condition") String condition,
										   @RequestParam(name = "price") String price)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		try 
		{
			if (name == null || name.isEmpty()) name = "*";
			if (category == null || category.isEmpty()) category = "*";
			if (condition == null || condition.isEmpty()) condition = "*";
			
			if (price == null || price.isEmpty()) return items.sortQuery(name, condition, category);
			else return items.sortQuery(name, condition, category, Double.parseDouble(price));
		}
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name was found.");
    	}
    }
     
    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
	public Collection<Item> findItemByCategory(@PathVariable("category") String category, @RequestParam(name = "sessid", required = true) String sessid)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

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
    public Collection<Item> findItemByCondition(@PathVariable("cond") String cond) {
    	try {
    	return items.findByCond(cond);
    	
	 } catch(Exception e) {
 		log.error(e.getMessage());
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this condition were found.");

 	}
 }
 
    
     
    @GetMapping("/items/name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
    	try {
		return items.findByCondAndName(name, cond);
		
    	}catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name & condition were found.");

    	}
    }
    
    @GetMapping("/items/category/{category}/cond/{cond}/price/{price}")
    public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price){
    	try {
		return items.findByCondAndCategoryAndPrice(cond, category, price);
    	} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No items with this name & category & condition & price limit were found in the cymarket");

    	}
    }
	
	// Red
    @RequestMapping(value = "/items/delete/all", method = RequestMethod.DELETE)
    public void deleteAll(@RequestParam(name = "sessid", required = true) String sessid)
    {
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }
        Session active = sessions.findBySessId(sessid);
		if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);
		
		if (active.getUser().getAdmin())
		{
			try
			{	
				items.deleteAll(); 
				log.info("Item Table Cleared: all items removed.");

			}
			catch (Exception e)
			{
				throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No items in database to remove.");
			}
		}
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this function.");
   }
}
