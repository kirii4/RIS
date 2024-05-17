package org.example.services.impl;

import com.nikak.pspkurssecurity.dto.ProfileResponse;
import com.nikak.pspkurssecurity.entities.Client;
import com.nikak.pspkurssecurity.entities.Session;
import com.nikak.pspkurssecurity.entities.User;
import com.nikak.pspkurssecurity.repositories.ClientRepository;
import com.nikak.pspkurssecurity.repositories.ClientSessionRepository;
import com.nikak.pspkurssecurity.repositories.AnalystSessionRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    private final ClientSessionRepository clientSessionRepository;
    private final AnalystSessionRepository analystSessionRepository;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(()->new UsernameNotFoundException("User not found"));
            }
        };
    }

    public ProfileResponse getProfileInfo(String email) {
        Comparator<Session> comp2 = (o1, o2) -> o2.getSessionDate().compareTo(o1.getSessionDate());

        ProfileResponse profileResponse = new ProfileResponse();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        profileResponse.setUser(user);
        switch (user.getRole()){
            case ADMIN -> {
                List<Session> sessions = new ArrayList<>();
                sessions.addAll(analystSessionRepository.findAll());
                sessions.sort(comp2);
                profileResponse.setSessions(sessions);
                profileResponse.setRole(user.getRole());
                break;
            }
            case CLIENT -> {
                Client client = clientRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new IllegalStateException("no such client with userid " + user.getId()));

                List<Session> sessions = new ArrayList<>();
                sessions.addAll(clientSessionRepository.findByClientId(client.getId()));
                sessions.sort(comp2);
                profileResponse.setSessions(sessions);
                profileResponse.setRole(user.getRole());
                break;
            }
        }
        return profileResponse;
    }
}
