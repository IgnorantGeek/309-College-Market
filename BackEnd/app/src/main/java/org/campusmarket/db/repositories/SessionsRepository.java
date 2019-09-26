package org.campusmarket.db.repositories;

import org.campusmarket.app.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionsRepository extends JpaRepository<Session, Integer>
{
    @Query("SELECT s FROM sessions s WHERE s.Id = (:sessId)")
    Session findById(@Param("sessId") String sessId);

    @Query("SELECET s FROM sessions s WEHERE s.user = (:userId)")
    Session findByUser(@Param("userId") int userId);
}