/*
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@RequestMapping("/all")
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

	 
	@PostMapping("/new")
	public void newItem(@RequestBody Item item, @RequestParam(name = "sessid", required = true) String sessid)
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		try
		{	
			User u=users.findById(sessions.findUserBySession(sessid));
			item.setUser(u);
			items.save(item);
			
        log.info(" success: a new item was created with a reference number(keep for your record): " + item.getRefnum());

		}
		catch (Exception e)
		{
	        log.error(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no such user with this username so sorry we won't be able to add your item :(",e);
	    }
	}
	
	
    @PutMapping("/update/{refnum}")
	public void updateItem(@RequestBody Item item, 
							@PathVariable (value = "refnum") int refnum,
							@RequestParam(name = "sessid", required = true) String sessid) 
	{
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		User user = users.findById(sessions.findUserBySession(sessid));

		if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find user.");

		Item oldItem = items.findByRefnum(refnum);

		if (oldItem == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with refnum: " + refnum);

		if (active.getAdmin() || item.getUser().getUsername().compareTo(user.getUsername()) == 0)
		{
			try 
			{
				oldItem.setName(item.getName());
				oldItem.setPrice(item.getPrice());
				oldItem.setCategory(item.getCategory());
				oldItem.setCondition(item.getCondition());
				items.save(oldItem);
					
				log.info(" success: the item with a reference number of " + refnum +" was updated");
			} 
			catch (Exception e)
			{
				log.error(e.getMessage());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Failure: Could not update the item with refnum: " + refnum);
			}
		}
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");

    }
    
    
    
    @RequestMapping(value = "/delete/{refnum}", method = RequestMethod.DELETE)
	public void deleteItem( @PathVariable(value = "refnum") int refnum,
							@RequestParam(name = "sessid", required = true) String sessid)
    {
    	if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		User user = users.findById(sessions.findUserBySession(sessid));

		if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find user.");

		Item oldItem = items.findByRefnum(refnum);

		if (oldItem == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with refnum: " + refnum);
		
		if (active.getAdmin() || oldItem.getUser().getUsername().compareTo(user.getUsername()) == 0)
		{
			try 
			{
				items.deleteById(refnum);
				log.info(" success: the item with a reference number of " + refnum +" was deleted");
				
			}
			catch (Exception e)
			{
				log.error("item with refnum"+ refnum + " not found" );
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item with refnum " + refnum + " not found.", e);
			}
		}
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
    }
    
    @GetMapping("/seller/{username}")
	public ArrayList<Item> findItemBySeller(@PathVariable("username") String seller)
	{	
		try 
		{
    		return items.findBySeller(seller);
		}
		catch(Exception e)
		{
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no items for sale.");
    	}
    }
     
           
    @GetMapping("/name/{name}")
    public Collection<Item> findItemByName(@PathVariable("name") String name) {
    	
    	try {
    	return items.findByName(name);
    	
    	} catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name was found in the cymarket");

    	}
    }
     
    @GetMapping("/category/{category}")
    public Collection<Item> findItemByCategory(@PathVariable("category") String category) {
    	try {
    	return items.findByCategory(category);
    	
    	 } catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this category was found in the cymarket");

    	}
    }
    
    
    
    @GetMapping("/cond/{cond}")
    public Collection<Item> findItemByCondition(@PathVariable("cond") String cond) {
    	try {
    	return items.findByCond(cond);
    	
	 } catch(Exception e) {
 		log.error(e.getMessage());
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this condition was found in the cymarket");

 	}
 }
 
    
     
    @GetMapping("/name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
    	try {
		return items.findByCondAndName(name, cond);
		
    	}catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & condition was found in the cymarket");

    	}
    }
    
    @GetMapping("/category/{category}/cond/{cond}/price/{price}")
    public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price){
    	try {
		return items.findByCondAndCategoryAndPrice(cond, category, price);
    	} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & category & condition & price limit was found in the cymarket");

    	}
    }
    
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll(@RequestParam(name = "sessid", required = true) String sessid)
    {
		if (sessid.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request Invalid: Empty value for required parameter 'sessid'.");
        }

        Session active = sessions.findBySessId(sessid);
        
        if (active == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find an active session with id: " + sessid);

		if (active.getAdmin())
		{
			try
			{
				items.deleteAll(); 
				log.info("User Table Cleared: all users removed.");
			} 
			catch (Exception e) 
			{
				log.error("No items in database to remove.");
				throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No items in database to remove.");
			}
		}
		else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");
   }
}
*/