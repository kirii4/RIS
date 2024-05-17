package org.example.dto;

import com.nikak.pspkurssecurity.entities.Payment;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SessionRequest {
    private Long analystId;
    private Long favorId;
    private Payment payment;
    private LocalDate sessionDate;
    private Long timeId;
    private Long clientId;
}
