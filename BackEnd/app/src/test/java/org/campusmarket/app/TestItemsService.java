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


/**
 * A  class to perform mockito tests for items 
 * @author fadelalshammasi
 *
 */
public class TestItemsService {
	
	@InjectMocks
	ItemService itemService;

	
	@Mock
	ItemsRepository repo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test //test 1 ~FA
	public void getItemByRefnumTest() {
		when(repo.findByRefnum(1)).thenReturn(new Item (1,"Introduction to Algorithms",80.00,"Book", "Ok", 
				new User("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false)));
		Item item=itemService.findByRefnum(1);
		
		assertEquals("Introduction to Algorithms", item.getName());
		assertEquals(80.00, item.getPrice(),0.01d);
		assertEquals("Book",item.getCategory());
		assertEquals("Ok",item.getCondition());
		assertEquals("Fadelsh",item.getUser().getUsername());
		
	}
	
	@Test //test 2 ~FA
	public void getAllItemTest() {
		List <Item> lst= new ArrayList<Item>();
		
		User userOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		Item itemOne=new Item (1,"BMW M4",9999.99,"Car", "Very good",userOne );
		Item itemTwo=new Item (2,"PS4",200.00,"Entertainment", "used",userOne );
		Item itemThree=new Item (3,"MacBook Pro",950.00,"Electronics & Computers", "used",userOne );
		Item itemFour=new Item (4,"Heavy rain",30.00,"video games", "used",userOne);


		
		lst.add(itemOne);
		lst.add(itemTwo);
		lst.add(itemThree);
		lst.add(itemFour);
		
		when (repo.findAll()).thenReturn(lst);
		
		List<Item>itemList=itemService.getAllItemList();
		
		assertEquals(4,itemList.size());
		verify (repo, times(1)).findAll();
		

	}
	
	@Test //test 3 ~FA
	public void getBySellerTest() {
		
    	ArrayList <Item> lst= new ArrayList<Item>();
		
		User sellerOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		User sellerTwo= new User ("SM", "Coms309","Simanta","Mitra","smitra@iastate.edu","isu", true);
		
		Item itemOne=new Item (1,"Cat",999.99,"Pet", "in good shape",sellerTwo );
		Item itemTwo=new Item (2,"BMW M4",9999.99,"Car", "Very good",sellerOne);
		Item itemThree=new Item (3,"PS4",200.00,"Video games", "used",sellerTwo );

		
		lst.add(itemOne);
		lst.add(itemThree);
	// I don't add itemTwo because the test will find items with SM as seller, not anything else
			
		when(repo.findBySeller("SM")).thenReturn(lst);
		
		List<Item>itemList=itemService.findBySeller("SM");
		List<Item>itemList2=itemService.findBySeller("SM"); //not using this list but only wanted to call findBySeller("SM") twice to test verify with times(2)

		
		assertEquals(2,itemList.size());
		assertNotEquals("Fadelsh", itemList.get(0).getUser().getUsername());  //we didn't add the item belong to Fadelsh
		assertEquals("SM", itemList.get(0).getUser().getUsername()); 
		assertEquals(itemThree.getUser().getUsername(), itemList.get(1).getUser().getUsername()); //just another way of doing the first argument for itemThird 
		verify(repo, times(2)).findBySeller("SM"); //was called twice
		
	}
	
	
}
