package org.campusmarket.app.controllers;

import java.util.Collection;
import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.db.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/items")


public class ItemController {
	@Autowired
    private ItemsRepository items;
	
	@RequestMapping("/all")
	public List<Item> getAll(){
	    return items.findAll();
	 }
	@PostMapping("/new")
	public void newItem(@RequestBody Item item){
        items.save(item);
    }

    @GetMapping("name/{name}")
    public Collection<Item> findItemByName(@PathVariable("name") String name) {
    	return items.findByName(name);
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
    
    @RequestMapping(value = "/delete/all", method = RequestMethod.DELETE)
    public void deleteAll()
    {
         items.deleteAll();   
    }
}
