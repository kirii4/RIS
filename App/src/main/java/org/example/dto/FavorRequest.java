package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class FavorRequest {
    private String name;
    private MultipartFile file;
}
