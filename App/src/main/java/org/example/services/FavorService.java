package org.example.services;

import com.nikak.pspkurssecurity.entities.Favor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FavorService {
    String createFavor(MultipartFile file, String name, Double price) throws IOException;

    String updateFavorImage(Long favorId, MultipartFile file) throws IOException;

    String updateFavorName(Long favorId, String name);
    List<Favor> findFavorsByAnalystId(Long analystId);
    String deleteFavor(Long favorId) throws IOException;
   // Subject getSubjectById(Long id);

    List<Object[]> findFavorsWithCounts(String name);
    byte[] getFavorImage(String filename) throws IOException;

    List<Favor> findAllFavors();

    List<Favor> getMostPopularFavors(PageRequest pageRequest);

    List<Favor> findAll();

    String updateFavorPrice(Long favorId, double price);
}
