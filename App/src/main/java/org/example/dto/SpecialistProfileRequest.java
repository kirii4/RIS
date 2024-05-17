package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AnalystProfileRequest {
    private String name;
    private String info;
    private Set<Long> favorsIds;
}
