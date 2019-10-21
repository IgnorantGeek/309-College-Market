package org.campusmarket.app;
import static org.junit.Assert.*;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.ItemService;
import org.campusmarket.app.models.User;
import org.campusmarket.db.repositories.ItemsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class TestItemsService {
	
	@InjectMocks
	ItemService itemService;

	
	@Mock
	ItemsRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	//tests to come here
	@Test
	public void getAllItemTest() {
		List <Item> lst= new ArrayList<Item>();
		
		User userOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		Item itemOne=new Item (1,"BMW M4",9999.99,"Car", "Very good",userOne );
		Item itemTwo=new Item (2,"PS4",200.00,"Video games", "used",userOne );
		
		lst.add(itemOne);
		lst.add(itemTwo);
		
		when (repo.findAll()).thenReturn(lst);
		
		List<Item>itemList=itemService.getAllItemList();
		
		assertEquals(2,itemList.size());
		verify (repo, times(1)).findAll();
		

	}
	
	@Test
	public void getbySellerTest() {
		
    	ArrayList <Item> lst= new ArrayList<Item>();
		
		User sellerOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		User sellerTwo= new User ("SM", "Coms309","Simanta","Mitra","smitra@iastate.edu","isu", true);
		
		Item itemOne=new Item (1,"Cat",999.99,"Pet", "in good shape",sellerTwo );
		Item itemTwo=new Item (2,"PS4",200.00,"Video games", "used",sellerOne );
		Item itemThree=new Item (2,"PS4",200.00,"Video games", "used",sellerTwo );

		
		lst.add(itemOne);
		lst.add(itemThree);
	
			
		when(repo.findBySeller("SM")).thenReturn(lst);
		
		List<Item>itemList=itemService.findBySeller("SM");
		
		assertEquals(2,itemList.size());
		assertNotEquals("Fadelsh", itemList.get(0).getUser().getUsername());
		assertEquals("SM", itemList.get(0).getUser().getUsername());
		assertEquals("SM", itemList.get(1).getUser().getUsername());

	

		
		
	}
	
	
}
