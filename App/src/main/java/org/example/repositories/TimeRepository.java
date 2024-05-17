package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time, Long> {
    @Query(value = "SELECT t" +
            " from Time t" +
            " where t.id=?1")
    Optional<Time> findById(Long id);

    @Query(value = "SELECT time.id, time.time" +
            " from time" +
            " inner join session"+
            " on session.time_id != time.id "+
            " and session.session_date=?1 and session.analyst_id=?2",nativeQuery = true )
    Optional<List<Time>> findFreeTime(Date date, Long analystId);

    /*@Query("Select t from Time t inner join t.session s where s.sessionDate=?1 and s.analyst.id=?2",nativeQuery = true )
    Optional<List<Time>> findFreeTime(String date, Long analystId);*/

    @Query(value = "SELECT t from Time t")
    Optional<List<Time>> getAllTime();

}
