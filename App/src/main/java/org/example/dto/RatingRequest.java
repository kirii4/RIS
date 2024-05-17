package org.example.dto;


import lombok.Data;

@Data
public class RatingRequest {
    private Long analystId;
    private Integer rating;
    private String comment;
}
