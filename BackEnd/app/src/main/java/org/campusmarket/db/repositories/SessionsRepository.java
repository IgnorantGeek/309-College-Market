package org.campusmarket.db.repositories;

import java.util.ArrayList;

import org.campusmarket.app.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SessionsRepository extends JpaRepository<Session, String>
{
    Session findBySessId(@Param("sessid") String sessid);

    @Query(nativeQuery = true, value="SELECT * FROM user_sessions where sess_id=:sess_id")
    @Transactional(readOnly = true)
    int findUserBySession(@Param("sess_id") String sessid);

    @Query(nativeQuery = true, value =  "SELECT * FROM user_sessions WHERE user_id=:user_id")
    @Transactional(readOnly = true)
    ArrayList<Session> findAllByUserId(@Param("user_id") int user_id);
}