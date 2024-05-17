package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Favor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {
    @Query("SELECT s.id, s.name, s.filename, s.price, COALESCE(COUNT(t), 0)  FROM Favor s " +
            "left JOIN s.analysts t where s.name like %?1%" +
            " group by s order by COALESCE(COUNT(t), 0) DESC ")
    List<Object[]> findFavorsWithCounts(String name); //для cтраницы со всеми услугами


    @Query("SELECT f FROM Favor f WHERE f.name = ?1")
    Optional<Favor> findFavorByName(String name);

    @Query("SELECT f FROM Favor f WHERE f.filename = ?1")
    Optional<Favor> findFavorByFileName(String filename);

    @Query("SELECT f FROM Favor f join f.analysts s where s.id = ?1")
    List<Favor> findFavorsByAnalystId(Long analystId);

    @Query("SELECT f FROM Favor f ")
    List<Favor> findAllFavors();

    @Query("SELECT s, COALESCE(COUNT(t), 0)  FROM Favor s " +
            " left JOIN s.analysts t " +
            " group by s order by COALESCE(COUNT(t), 0) DESC ")
    List<Favor> getMostPopularFavors(PageRequest pageRequest);

}
