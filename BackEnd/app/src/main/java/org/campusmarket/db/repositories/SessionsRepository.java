package org.campusmarket.db.repositories;

import java.util.ArrayList;

import org.campusmarket.app.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, String>
{
    Session findBySessId(@Param("sessid") String sessid);

    @Query(nativeQuery = true, value = "SELECT * FROM sessions WHERE userid=:userid")
    ArrayList<Session> findAllByUserId(@Param("userid") int userid);
}