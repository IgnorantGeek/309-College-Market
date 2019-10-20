package org.campusmarket.app;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.campusmarket.app.models.ItemService;
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
}
