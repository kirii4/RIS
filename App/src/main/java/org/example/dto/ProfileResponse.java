package org.example.dto;

import com.nikak.pspkurssecurity.entities.*;
import lombok.Data;

import java.util.List;

@Data
public class ProfileResponse {
    private User user;
    private Role role;
    private List<Session> sessions;

}
