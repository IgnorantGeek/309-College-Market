package org.campusmarket.app.controllers;

import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.db.repositories.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Item findItemByName(@PathVariable("name") String name) {
    	return items.findByName(name);
    }
    
    @GetMapping("category/{category}")
    public Item findItemByCategory(@PathVariable("category") String category) {
    	return items.findByCategory(category);
    }
    
    /* issue if result is not unique 
    @GetMapping("cond/{cond}")
    public Item findItemByCondition(@PathVariable("cond") String cond) {
    	return items.findByCond(cond);
    }
     */
	
}
