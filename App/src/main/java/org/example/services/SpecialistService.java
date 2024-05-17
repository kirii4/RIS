package org.example.services;

import com.nikak.pspkurssecurity.dto.AnalystProfileRequest;
import com.nikak.pspkurssecurity.entities.Analyst;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AnalystService {
   /* Teacher uploadPic(MultipartFile file, String email) throws IOException;


    Teacher getTeacherById(Long id);*/

 String addCertificate(Long analystId, MultipartFile file) throws IOException;

 String deleteCertificate(Long analystId, Long certificateId) throws IOException;

 String createAnalyst(MultipartFile file, String name, String info) throws IOException;
   List<Analyst> findAnalystsByFavorId(Long favorId);
 List<Analyst> getAnalystsByFavorId(Long favorId, boolean order);


    byte[] getAnalystImage(String filename) throws IOException;

    String updateAnalystImage(Long analystId, MultipartFile file) throws IOException;

    String deleteAnalystImage(Long analystId) throws IOException;

    Analyst updateAnalystProfile(AnalystProfileRequest analystProfileRequest, Long analystId);

    String deleteAnalyst(Long analystId) throws IOException;

 Analyst assignFavorToAnalyst(Long analystId, Long favorId);

 Analyst deleteFavorFromAnalyst(Long analystId, Long favorId);

 String updateAnalystName(Long analystId, String name);

 byte[] getCertificateImage(String filename) throws IOException;

 Optional<Analyst> findById(Long analystId);

 String updateAnalystInfo(Long analystId, String info);

 List<Analyst> findAll();
 String updateAnalystImageCertificate(Long analystId, MultipartFile file) throws IOException;

}
