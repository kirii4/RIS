package org.example.services.impl;

import com.nikak.pspkurssecurity.dto.AnalystProfileRequest;
import com.nikak.pspkurssecurity.entities.Certificate;
import com.nikak.pspkurssecurity.entities.Favor;
import com.nikak.pspkurssecurity.entities.Analyst;
import com.nikak.pspkurssecurity.repositories.CertificateRepository;
import com.nikak.pspkurssecurity.repositories.FavorRepository;
import com.nikak.pspkurssecurity.repositories.AnalystRepository;
import com.nikak.pspkurssecurity.repositories.UserRepository;
import com.nikak.pspkurssecurity.services.AnalystService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalystServiceImpl implements AnalystService {
    private final String FOLDER_PATH = "E:/курсач5сем/images";
    private final AnalystRepository analystRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    private final FavorRepository favorRepository;

    public String updateAnalystImage(Long analystId, MultipartFile file) throws IOException {

        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("no such analyst with id "));

        if (file != null) {
            if (analyst.getFilename() != null) {

                String filename = analyst.getFilename();
                Path saveTO = Paths.get(FOLDER_PATH + filename);
                Files.delete(saveTO);
                Files.copy(file.getInputStream(), saveTO);
            }
            if (analyst.getFilename() == null) {
                String filename = UUID.randomUUID().toString() + ".jpg";
                Path saveTO = Paths.get(FOLDER_PATH + filename);

                analyst.setFilename(filename);
                // savedData =  subjectRepository.save(subjectToSave);
                Files.copy(file.getInputStream(), saveTO);
            }
        }

        Analyst savedAnalyst = analystRepository.save(analyst);
        if (savedAnalyst != null) return "analyst pic : " + savedAnalyst.getFilename() + " updated successfully";
        return null;

    }

    public String updateAnalystImageCertificate(Long analystId, MultipartFile file) throws IOException {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("No such analyst with id " + analystId));

        Certificate certificate = analyst.getCertificate();
        if (certificate == null) {
            certificate = new Certificate();
            analyst.setCertificate(certificate);
        }


        if (file != null) {
            if (certificate.getFileName() != null) {
                String filename = certificate.getFileName();
                Path saveTo = Paths.get(FOLDER_PATH + filename);
                Files.delete(saveTo);
                Files.copy(file.getInputStream(), saveTo);
            } else {
                String filename = UUID.randomUUID().toString() + ".jpg";
                Path saveTo = Paths.get(FOLDER_PATH + filename);
                certificate.setFileName(filename);
                Files.copy(file.getInputStream(), saveTo);
            }
        }
        System.out.println(file);
        Analyst savedAnalyst = analystRepository.save(analyst);
        if (savedAnalyst != null) {
            return "Analyst certificate pic2: " + savedAnalyst.getCertificate().getFileName() + " updated successfully";
        }
        return null;
    }

    @Transactional
    public Analyst updateAnalystProfile(AnalystProfileRequest analystProfileRequest, Long analystId) {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("no such analyst with id"));
        System.out.println("analyst found");
        if (analystProfileRequest.getName() != null) {
            analyst.setName(analystProfileRequest.getName());
        }
        if (analystProfileRequest.getInfo() != null) {
            analyst.setInfo(analystProfileRequest.getInfo());
        }
        if (analystProfileRequest.getFavorsIds() != null) {
            Set<Favor> favors = new HashSet<>();
            for (Long id : analystProfileRequest.getFavorsIds()) {
                System.out.println("favor");
                Optional<Favor> favor = favorRepository.findById(id);
                if(favor.isPresent()) {
                    favors.add(favor.get());
                }
            }
            analyst.setAnalystFavors(favors);
        }
        return analystRepository.save(analyst);

    }

    public String deleteAnalystImage(Long analystId) throws IOException {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("no such analyst with id"));

        if (analyst.getFilename() != null) {
            Path saveTO = Paths.get(FOLDER_PATH + analyst.getFilename());
            Files.delete(saveTO);
            analyst.setFilename(null);
        }

        Analyst savedAnalyst = analystRepository.save(analyst);
        if (savedAnalyst != null) return "analyst pic deleted successfully";
        return null;
    }
    /*public Teacher uploadPic(MultipartFile file, String email) throws IOException{

        User user  = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("no such user"));
        System.out.println(user);
        Teacher teacher = teacherRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalStateException("no such teacher with userid "+ user.getId()));
        System.out.println(teacher);
        if(file != null){
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            teacher.setFilename(filename);
            teacher.setType(file.getContentType());
            teacher.setData(file.getBytes());
        }

        return teacherRepository.save(teacher);
    }*/

    public  String addCertificate(Long analystId, MultipartFile file) throws IOException {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("no such analyst"));

        if (file != null) {
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path saveTO = Paths.get(FOLDER_PATH + filename);
            Files.copy(file.getInputStream(), saveTO);
            certificateRepository.save(
                    new Certificate(filename, analyst)
            );

            return "certificate added successfully";
        }
        return null;
    }

    public String deleteCertificate(Long analystId, Long certificateId) throws IOException {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException("no such analyst"));
        Certificate certificate = certificateRepository.findByIdAndAnalyst(certificateId, analystId)
                .orElseThrow(() -> new IllegalStateException("no such certificate with id " + certificateId));
        Path saveTO = Paths.get(FOLDER_PATH + certificate.getFileName());
        Files.delete(saveTO);
        certificateRepository.deleteById(certificateId);

        return "certificate deleted successfully";

    }
    public List<Analyst> findAnalystsByFavorId(Long favorId) {
        return analystRepository.findByFavorId(favorId);
    }
    public byte[] getCertificateImage(String filename) throws IOException {
        Optional<Certificate> im = certificateRepository.findCertificateByFileName(filename);
        return Files.readAllBytes(
                new File(FOLDER_PATH + im.get().getAnalyst().getCertificate().getFileName()).toPath());

    }
    public byte[] getAnalystImage(String filename) throws IOException {
        Optional<Analyst> im = analystRepository.findAnalystByFileName(filename);
        return Files.readAllBytes(
                new File(FOLDER_PATH + im.get().getFilename()).toPath());

    }

    public Optional<Analyst> findAnalystById(Long id) {
        return analystRepository.findById(id);
    }

    public String deleteAnalyst(Long analystId) throws IOException {
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(()->
                        new IllegalStateException("analyst does not exist")
                );
       /* Path deletePath = Paths.get(FOLDER_PATH + analyst.getFilename());
        Files.delete(deletePath);*/
        analystRepository.deleteById(analystId);
        return "analyst "+ analyst.getName()+ " deleted successfully";
    }

    public String createAnalyst(MultipartFile file, String name, String info) throws IOException{
        Analyst analystToSave = new Analyst();
        analystToSave.setName(name);
        analystToSave.setInfo(info);
        Analyst savedData;
        if(file != null){
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path saveTO = Paths.get(FOLDER_PATH + filename);

            analystToSave.setFilename(filename);
            savedData =  analystRepository.save(analystToSave);
            Files.copy(file.getInputStream(), saveTO);
        } else savedData =  analystRepository.save(analystToSave);

        if(savedData!=null) return "analyst : "+name+" added successfully";
        return null;
    }

    public Analyst assignFavorToAnalyst(Long analystId, Long favorId) {

        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException(
                        "analyst with id " + analystId + " does not exist"
                ));
        System.out.println(analyst.getAnalystFavors());
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(() -> new IllegalStateException(
                        "favor with id " + favorId + " does not exist"
                ));
        Set<Favor> favorSet = analyst.getAnalystFavors();
        System.out.println("favorset"+favorSet);
        favorSet.add(favor);
        analyst.setAnalystFavors(favorSet);
        System.out.println(analyst.getAnalystFavors());
        analystRepository.save(analyst);
        return  analyst;
    }

    public Analyst deleteFavorFromAnalyst(Long analystId, Long favorId) {
        Set<Favor> favorSet = null;
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(() -> new IllegalStateException(
                        "analyst with id " + analystId + " does not exist"
                ));
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(() -> new IllegalStateException(
                        "favor with id " + favorId + " does not exist"
                ));
        favorSet = analyst.getAnalystFavors();
        favorSet.remove(favor);
        analyst.setAnalystFavors(favorSet);
        return analystRepository.save(analyst);
    }

    public  String updateAnalystName(Long analystId, String name){
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(()->
                        new IllegalStateException("analyst does not exist")
                );
        if(name!=null){
            analyst.setName(name);
        }
       Analyst saved = analystRepository.save(analyst);
        if(saved!=null) return "successfully updated analyst: "+ name;
        return "error";
    }

    public  String updateAnalystInfo(Long analystId, String info){
        Analyst analyst = analystRepository.findById(analystId)
                .orElseThrow(()->
                        new IllegalStateException("analyst does not exist")
                );
        if(info!=null){
            analyst.setInfo(info);
        }
        Analyst saved = analystRepository.save(analyst);
        if(saved!=null) return "successfully updated analyst: "+ info;
        return "error";
    }

    public List<Analyst> findAll(){
        return analystRepository.findAll();
    }
    public List<Analyst> getAnalystsByFavorId(Long favorId, boolean order) {
        Comparator<Analyst> finalRatingComp1 = Comparator.comparingDouble(Analyst::getFinalRating);
        Comparator<Analyst> finalRatingComp2 = (o1, o2) -> Double.compare(o2.getFinalRating(), o1.getFinalRating());


                List<Analyst> list = analystRepository.findByFavorId(favorId);
                if (order) {
                    list.sort(finalRatingComp2);
                } else {
                    list.sort(finalRatingComp1);
                }
                return list;
    }

    public Optional<Analyst> findById(Long analystId){
        return analystRepository.findById(analystId);
    }
}
