package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>
{
    @Query(value = "SELECT s from Session s where s.analyst.id=?1 AND s.sessionDate = ?2 AND s.time.id=?3")
    Optional<Session> findSessionBySessionDateAndSessionTime(Long analystId, LocalDate sessionDate, Long timeId);

    @Query(value = "SELECT s" +
            " from Session s" +
            " where s.id=?1")
    Optional<Session> findSessionById(Long id);
    @Query(value = "SELECT s" +
            " from Session s")
    List<Session> findAllSessions();

    @Query(value = "SELECT s from Session s where s.analyst.id=?1")
    Optional<List<Session>> findSessionsByAnalystId(Long analystId);

}
