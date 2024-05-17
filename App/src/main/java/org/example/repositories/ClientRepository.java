package org.example.repositories;

import com.nikak.pspkurssecurity.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT s FROM Client s where s.user.id = ?1")
    Optional<Client> findByUserId(Long userId);
}
