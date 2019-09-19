package org.campusmarket.db.repositories;

import org.campusmarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<User, Integer>
{

}