package org.campusmarket.db.repositories;

import java.util.List;

import org.campusmarket.app.models.Session;
import org.campusmarket.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, String>
{
    Session findBySessId(@Param("sessid") String sessId);
    List<Session> findByUser(@Param("user") User user);
}