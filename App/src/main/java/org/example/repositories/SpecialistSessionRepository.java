package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalystSessionRepository extends JpaRepository<Session, Long> {

    @Query("select t from Session  t where t.analyst.id = ?1")
    List<Session> findByAnalystId(Long analystId);

    @Query("select t from Session  t where t.client.id = ?1")
    List<Session> findByClientId(Long clientId);
}