package org.collegemarket.db.repositories;

import org.collegemarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<User, Integer>
{

}