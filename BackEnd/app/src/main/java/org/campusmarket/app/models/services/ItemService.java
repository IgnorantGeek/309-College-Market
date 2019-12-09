package org.campusmarket.app.models.services;

import java.util.ArrayList;
import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.app.exception.MyFileNotFoundException;
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
	
	
	/*
	 public Item storeFile(MultipartFile file) {
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Filename contains invalid path characters " + fileName);
	            }

	            Item f = new Item(fileName, file.getContentType(), file.getBytes());

	            return repo.save(f);
	        } catch (IOException ex) {
	            throw new FileStorageException("Couldn't save file into the db " + fileName + ". Please try again", ex);
	        }
	    }
*/
	    /**
	     * A method to search for a file in the db
	     * @param id
	     * @return the file found otherwise throwing an exception
	     */
	    public Item getFile(int refnum) {
	    	try {
	        return repo.findByRefnum(refnum);
	    	}
	    	
	    	catch (MyFileNotFoundException e) {
	    		throw new MyFileNotFoundException("Couldn't find file with id " + refnum);
	    	}
	    }
	    public ArrayList<Item>findByCategory( @Param("category") String category){
			 return repo.findByCategory(category);
		}
		  
	    public ArrayList<Item>findByCondAndName( @Param("name") String name, @Param("cond") String cond){
			 return repo.findByCondAndName(name, cond);
		}
	    
	    
	}

	

