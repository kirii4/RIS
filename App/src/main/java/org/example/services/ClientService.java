package org.example.services;

import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.entities.Session;

import java.util.List;

public interface ClientService {
    String addRating(RatingRequest ratingRequest, String email);
    String deleteRatingById(Long id, String email);

    List<Object[]> getRatings(String email);

    List<Session> getClientSessions(String email);
}
