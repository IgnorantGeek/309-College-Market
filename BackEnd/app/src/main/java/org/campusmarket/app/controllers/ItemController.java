package org.campusmarket.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.ItemService;
import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;
import org.campusmarket.app.models.fileResponse;
import org.campusmarket.db.repositories.ItemsRepository;
import org.campusmarket.db.repositories.SessionsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



/**
 * a class to represent the controller for items
 * @author fadelsh
 * @author nheisler
 *
 */
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
	
	@Autowired
	private ItemService files;

	
	Log log = LogFactory.getLog(ItemController.class);
	
	
	/**
	 * A method to get all the items
	 * @return a list of all items 
	 */
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

	 /**
	  * A method to post a new item 
	  * @param the body of the item model class
	  * @param sessid of the user posting the item
	  */
		@PostMapping("/new")
	public Item newItem(@RequestBody Item item, @RequestParam(name = "sessid", required = true) String sessid)
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
			
		//	Item f=files.storeFile(file);
		//	 String downloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(f.getRefnum()+"").toUriString(); 
		//	 fileResponse fr=new fileResponse(f.getFname(),downloadUrl, file.getContentType(),file.getSize()); //size is in byte 
			
			
			item.setUser(u);
			items.save(item);
			
        log.info(" success: a new item was created with a reference number(keep for your record): " + item.getRefnum());
        return item;

		}
		catch (Exception e)
		{
	        log.error(e.getMessage());
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There's no such user with this username so sorry we won't be able to add your item :(",e);
	    }
	}
	/**
	 * A method to update an item 
	 * @param item
	 * @param refnum
	 * @param sessid
	 */
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
		if(users.existsByUserName(user.getUsername())==0) {
			log.error("There's no such user with this username in our system so sorry we can't update this item");
			return;
		}

		Item oldItem = items.findByRefnum(refnum);

		if (oldItem == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find item with refnum: " + refnum);

		if (active.getAdmin() || oldItem.getUser().getUsername().equals(user.getUsername()))
		{
			try 
			{
				oldItem.setName(item.getName());
				oldItem.setPrice(item.getPrice());
				oldItem.setCategory(item.getCategory());
				oldItem.setCondition(item.getCondition());
				//oldItem.setImage(item.getImage());
				oldItem.setUser(item.getUser());
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
    
    
    /**
     * A method to delete an item
     * @param refnum
     * @param sessid
     */
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
		
		if(users.existsByUserName(user.getUsername())==0) {
			log.error("There's no such user with this username in our system so sorry we can't update this item");
			return;
		}

		
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
    
    /**
     * A method to get all the items for a specific seller
     * @param seller
     * @return an arrylist of the items for that seller
     */
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
     
      /**
       * A method to search item by their name (or part of the name)
       * @param name
       * @return a collection of the items that have that name(or part of it) sorted by price
       */
    @GetMapping("/name/{name}")
    public Collection<Item> findItemByName(@PathVariable("name") String name) {
    	
    	try {
    	return items.findByName(name);
    	
    	} catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name was found in the cymarket");

    	}
    }
     
    /**
     * A method to search for items by their category
     * @param category
     * @return  a collection of the items that have that category sorted by price 
     */
    @GetMapping("/category/{category}")
    public Collection<Item> findItemByCategory(@PathVariable("category") String category) {
    	try {
    	return items.findByCategory(category);
    	
    	 } catch(Exception e) {
    		log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this category was found in the cymarket");

    	}
    }
    
    
    /**
     * A method to search for items by their condition
     * @param cond
     * @return a collection of the items that have that condition sorted by price
     */
    @GetMapping("/cond/{cond}")
    public Collection<Item> findItemByCondition(@PathVariable("cond") String cond) {
    	try {
    	return items.findByCond(cond);
    	
	 } catch(Exception e) {
 		log.error(e.getMessage());
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this condition was found in the cymarket");

 	}
 }
 
    
     /**
      * a method to search for items using their names AND conditions 
      * @param name
      * @param cond
      * @return List of items with the name and condition provided (or part of them)
      */
    @GetMapping("/name/{name}/cond/{cond}")
    public Collection<Item>findByCondAndName(@PathVariable("name") String name ,@PathVariable("cond") String cond){
    	try {
		return items.findByCondAndName(name, cond);
		
    	}catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & condition was found in the cymarket");

    	}
    }
    
    /**
     * a method to search for items using their category AND condition AND the maximum price desired to narrow the search even more
     * @param name
     * @param cond
     * @return List of items with the category & condition & price provided sorted by price
     */
    @GetMapping("/category/{category}/cond/{cond}/price/{price}")
    public Collection<Item>findByCondPriceCategory(@PathVariable("cond") String cond ,@PathVariable("category") String category, @PathVariable("price") double price){
    	try {
		return items.findByCondAndCategoryAndPrice(cond, category, price);
    	} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No item with this name & category & condition & price limit was found in the cymarket");

    	}
    }
    
    /**
     * A method to delete all items in the campusmarket 
     * @param sessid
     */
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
    
    
    /**
	 * A method to post a file to the db
	 * @param file
	 * @return a response with the filename, url for downloading the file, type of file,and size of file in byte 
	 */
	 @PostMapping("/upload")
	 public fileResponse uploadFile(@RequestParam("fname") MultipartFile file) {
		Item f=files.storeFile(file);
		 String downloadUrl= ServletUriComponentsBuilder.fromCurrentContextPath().path("/items/download/").path(f.getRefnum()+"").toUriString();  
		 fileResponse fr=new fileResponse(f.getFname(),downloadUrl, file.getContentType(),file.getSize()); //size is in byte 
		 return fr;
		 
	 }
	 
	 
	 /**
	  * A method to display(download if using the browser) the content of the file
	  * @param id
	  * @return
	  */
	   @GetMapping("/download/{refnum}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable int refnum) {
	       
	        Item f = files.getFile(refnum);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(f.getFtype()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFname() + "\"")
	                .body(new ByteArrayResource(f.getImage()));
	    }

	/*   
	   @PutMapping("/update/{id}")
		public void updateFile(@RequestBody File file, @PathVariable (value = "id") int id) {
			File oldFile = repo.findById(id);
			oldFile.setFileName(file.getFileName());
			oldFile.setFtype(file.getFileType());
			oldFile.setFdata(file.getFileData());
			repo.save(file);
	   
	}	
    */
    
    
}

