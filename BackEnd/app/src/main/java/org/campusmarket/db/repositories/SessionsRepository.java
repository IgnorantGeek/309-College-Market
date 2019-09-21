package org.campusmarket.db.repositories;

import org.campusmarket.app.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Session, Integer>
{

}