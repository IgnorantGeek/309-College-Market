package org.campusmarket.app.controllers;

import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.db.repositories.ItemsRepository;
import org.campusmarket.db.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/items")


public class ItemController {
	@Autowired
    private ItemsRepository items;
	
	//more to come later
}
