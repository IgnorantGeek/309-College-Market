
package org.campusmarket.app.models.services.tests;
import static org.junit.Assert.*;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.campusmarket.app.models.Item;
import org.campusmarket.app.models.services.ItemService;
import org.campusmarket.app.models.User;
import org.junit.Test;
import org.mockito.InjectMocks;




public class TestItemsService extends TestServices {
	
	@InjectMocks
	private ItemService itemService;

	
	
	@Test //test 1 ~FA
	public void getItemByRefnumTest() {
		when(super.itemsRepo.findByRefnum(1)).thenReturn(new Item (1,"Introduction to Algorithms",80.00,"Book", "Ok", 
				new User("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false)));
		Item item=super.itemsRepo.findByRefnum(1);
		
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
		
		when (super.itemsRepo.findAll()).thenReturn(lst);
		
		List<Item>itemList=itemService.getAllItemList();
		
		assertEquals(4,itemList.size());
		verify (super.itemsRepo, times(1)).findAll();
		

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
			
		when(super.itemsRepo.findBySeller("SM")).thenReturn(lst);
		
		List<Item>itemList=itemService.findBySeller("SM");
		List<Item>itemList2=itemService.findBySeller("SM"); //not using this list but only wanted to call findBySeller("SM") twice to test verify with times(2)

		
		assertEquals(2,itemList.size());
		assertNotEquals("Fadelsh", itemList.get(0).getUser().getUsername());  //we didn't add the item belong to Fadelsh
		assertEquals("SM", itemList.get(0).getUser().getUsername()); 
		assertEquals(itemThree.getUser().getUsername(), itemList.get(1).getUser().getUsername()); //just another way of doing the first argument for itemThird 
		verify(itemsRepo, times(2)).findBySeller("SM"); //was called twice
		
	}
	
	@Test // //test 2 FA- Demo4
	public void getByNameAndCondTest() {
ArrayList <Item> lst= new ArrayList<Item>();
		
		User sellerOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		User sellerTwo= new User ("SM", "Coms309","Simanta","Mitra","smitra@iastate.edu","isu", true);
		
		
		Item itemOne=new Item (1,"PS4",200.00,"Video games", "used",sellerOne );
		Item itemTwo=new Item (2,"xBox",250.00,"Video games", "new",sellerTwo );
		Item itemThree=new Item (3,"Nintendo Switch",300.00,"Video games", "used",sellerTwo );
		Item itemFour=new Item (4,"xBox",435.00,"Video games", "new",sellerTwo );
		Item itemFive=new Item (5,"xBox",536.00,"Video games", "new",sellerOne );
		Item itemSix=new Item (6,"xBox",149.50,"Video games", "new",sellerOne );

		lst.add(itemFive);
		lst.add(itemSix);
		lst.add(itemFour);
		lst.add(itemTwo);
	
		
		when(super.itemsRepo.findByCondAndName("xBox", "new")).thenReturn(lst);
		
		List<Item>itemList=itemService.findByCondAndName("xBox", "new");
		List<Item>itemList2=itemService.findByCondAndName("xBox", "new");
		List<Item>itemList3=itemService.findByCondAndName("xBox", "new");
		
		
		assertEquals(4,itemList.size());
		assertEquals(itemFive.getName(),itemList.get(0).getName());
		assertEquals(itemFive.getCondition(),itemList.get(0).getCondition());
		assertEquals(itemSix.getName(),itemList.get(1).getName());
		assertEquals(itemSix.getCondition(),itemList.get(1).getCondition());
		assertEquals(itemFour.getName(),itemList.get(2).getName());
		assertEquals(itemFour.getCondition(),itemList.get(2).getCondition());
		assertEquals(itemTwo.getName(),itemList.get(3).getName());
		assertEquals(itemTwo.getCondition(),itemList.get(3).getCondition());
		verify(super.itemsRepo,times(3)).findByCondAndName("xBox", "new");


		
	}
	
	@Test //test 3 FA- Demo4
	public void getByCategoryTest() {
		
	ArrayList <Item> lst= new ArrayList<Item>();
		
		User sellerOne=new User ("Fadelsh", "abc123","Fadel","Alshammasi","fadelsh@iastate.edu","isu", false);
		User sellerTwo= new User ("SM", "Coms309","Simanta","Mitra","smitra@iastate.edu","isu", true);
		
		
		Item itemThree=new Item (3,"PS4",200.00,"Video games", "used",sellerOne );
		Item itemFour=new Item (4,"xBox",200.00,"Video games", "used",sellerTwo );
		


		lst.add(itemThree);
		lst.add(itemFour);
		
		when(super.itemsRepo.findByCategory("Video games")).thenReturn(lst);
		
		List<Item>itemList=itemService.findByCategory("Video games");
		assertEquals(2,itemList.size());
		assertEquals(itemThree.getCategory(), itemList.get(0).getCategory());
		assertEquals(itemFour.getCategory(), itemList.get(1).getCategory());
		verify(super.itemsRepo,times(1)).findByCategory("Video games");
		
	}
	
	
}

