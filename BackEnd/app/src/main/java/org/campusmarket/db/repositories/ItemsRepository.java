package org.campusmarket.db.repositories;

import org.campusmarket.app.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemsRepository extends JpaRepository<Item, Integer>
{

}
