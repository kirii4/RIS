package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Analyst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AnalystRepository
        extends JpaRepository<Analyst, Long> {

    @Query(value = "SELECT analyst.*" +
            " from analyst " +
            " inner join analyst_favor" +
            " on analyst.id = analyst_favor.analyst_id\n" +
            " where favor_id = ?1",
            nativeQuery = true)
    Optional<List<Analyst>> findAnalystsByFavorId(Long favor_id);

    @Query("SELECT s FROM Analyst s where s.id = ?1")
    Optional<Analyst> findById(Long analystId);

    @Query("SELECT s from Analyst s inner join s.analystFavors f where f.id = ?1")
    List<Analyst> findByFavorId(Long favorId);
    @Query("SELECT t FROM Analyst t WHERE t.filename = ?1")
    Optional<Analyst> findAnalystByFileName(String fileName);


}
