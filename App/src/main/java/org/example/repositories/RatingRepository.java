package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r FROM Rating r where r.client.id = ?1 and r.analyst.id = ?2")
    Optional<Rating> findByClientIdAndAnalystId(Long clientId, Long analystId);

    @Query("SELECT r FROM Rating  r where r.id = ?1 and r.client.id = ?2")
    Optional<Rating> findByIdAndClientId(Long id, Long studentId);

    @Query("SELECT r, r.analyst FROM Rating  r where r.client.id = ?1")
    List<Object[]> findByClientId(Long clientId);
}
