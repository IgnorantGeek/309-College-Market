package org.campusmarket.db.repositories;

import org.campusmarket.app.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.repository.query.Param;


public interface ItemsRepository extends JpaRepository<Item, Integer>
{


}
