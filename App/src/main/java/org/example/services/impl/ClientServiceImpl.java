package org.example.services.impl;

import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.entities.*;
import com.nikak.pspkurssecurity.repositories.*;
import com.nikak.pspkurssecurity.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final AnalystRepository analystRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ClientRepository clientRepository;
    private final ClientSessionRepository clientSessionRepository;

    public String addRating(RatingRequest ratingRequest, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such client with userid " + user.getId()));

        Analyst analyst = analystRepository.findById(ratingRequest.getAnalystId())
                .orElseThrow(() -> new IllegalStateException("no such analyst with id " + ratingRequest.toString()));


        Optional<Rating> rating = ratingRepository.findByClientIdAndAnalystId(client.getId(), analyst.getId());

        if(rating.isPresent()){
            rating.get().setRating(ratingRequest.getRating());
            rating.get().setComment(ratingRequest.getComment());
            ratingRepository.save(rating.get());
            return "rating updated successfully";
        } else {
            Rating newRating = new Rating();
            newRating.setClient(client);
            newRating.setAnalyst(analyst);
            newRating.setComment(ratingRequest.getComment());
            newRating.setRating(ratingRequest.getRating());
            ratingRepository.save(newRating);
            return "rating added successfully";
        }
    }

    public  String deleteRatingById(Long id, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such client with userid " + user.getId()));
        Rating rating = ratingRepository.findByIdAndClientId(id, client.getId())
                .orElseThrow(()->new IllegalStateException("no rating with this id"));
        ratingRepository.deleteById(id);
        return "rating deleted successfully";
    }


    public List<Object[]> getRatings(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such client with userid " + user.getId()));

        return  ratingRepository.findByClientId(client.getId());
    }
    public List<Session> getClientSessions(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));

        Client client = clientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such client with userid " + user.getId()));

        return clientSessionRepository.findByClientId(client.getId());
    }

}
