package org.example.services.impl;

import com.nikak.pspkurssecurity.entities.Favor;
import com.nikak.pspkurssecurity.repositories.FavorRepository;
import com.nikak.pspkurssecurity.services.FavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavorServiceImpl implements FavorService {
    private final String FOLDER_PATH = "E:/курсач5сем/images/";
    private final FavorRepository favorRepository;

    public String createFavor(MultipartFile file, String name, Double price) throws IOException{
        boolean favorIsPresent = favorRepository.findFavorByName(name).isPresent();
        if(favorIsPresent){
            throw new  IllegalStateException("name already taken");
        }
        Favor favorToSave = new Favor();
        favorToSave.setName(name);
        favorToSave.setPrice(price);
        Favor savedData;
        if(file != null){
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path saveTO = Paths.get(FOLDER_PATH + filename);

            favorToSave.setFilename(filename);
            savedData =  favorRepository.save(favorToSave);
            Files.copy(file.getInputStream(), saveTO);
        } else savedData =  favorRepository.save(favorToSave);
        System.out.println(savedData);
        if(savedData!=null) return "favor : "+name+" added successfully";
        return null;
    }

    public  String updateFavorName(Long favorId, String name){
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(()->
                        new IllegalStateException("favor does not exist")
                );
        if(name!=null){
            favor.setName(name);
        }
        Favor saved = favorRepository.save(favor);
        if(saved!=null) return "successfully updated favor: "+ name;
        return "error";
    }

    public  String updateFavorPrice(Long favorId, double price){
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(()->
                        new IllegalStateException("favor does not exist")
                );
        if (price >= 0) {
            favor.setPrice(price);
            Favor saved = favorRepository.save(favor);
            if (saved != null) {
                return "successfully updated favor: " + price;
            }
        }
        return "error";
    }

    public String updateFavorImage(Long favorId, MultipartFile file) throws IOException{
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(()->
                        new IllegalStateException("favor does not exist")
                );

        if(file!=null){
            if(favor.getFilename()!=null){

                String filename = favor.getFilename();
                Path saveTO = Paths.get(FOLDER_PATH + filename);
                Files.delete(saveTO);
                Files.copy(file.getInputStream(), saveTO);
            }
            if(favor.getFilename()==null){
                String filename = UUID.randomUUID().toString() + ".jpg";
                Path saveTO = Paths.get(FOLDER_PATH + filename);

                favor.setFilename(filename);
               // savedData =  subjectRepository.save(subjectToSave);
                Files.copy(file.getInputStream(), saveTO);
            }
        }
        Favor savedData =  favorRepository.save(favor);

        if(savedData!=null) return "favor pic : "+savedData.getFilename()+" updated successfully";
        return null;

    }

    public String deleteFavor(Long favorId) throws IOException {
        Favor favor = favorRepository.findById(favorId)
                .orElseThrow(()->
                        new IllegalStateException("favor does not exist")
                );
        Path deletePath = Paths.get(FOLDER_PATH + favor.getFilename());
        Files.delete(deletePath);
        favorRepository.deleteById(favorId);
        return "favor "+ favor.getName()+ " deleted successfully";
    }

    public List<Object[]> findFavorsWithCounts(String name){
        if (name == null) name = "";
        return favorRepository.findFavorsWithCounts(name);
    }

    public byte[] getFavorImage(String filename) throws IOException {
        Optional<Favor> im = favorRepository.findFavorByFileName(filename);

        return Files.readAllBytes(
                new File( FOLDER_PATH + im.get().getFilename()).toPath());
    }



    public List<Favor> findFavorsByAnalystId(Long analystId) {
        System.out.println("awrgtyrter");
        return favorRepository.findFavorsByAnalystId(analystId);
    }

    public List<Favor> findAllFavors() {
        return favorRepository.findAllFavors();
    }

    /*public Subject getSubjectById(Long id){
        return subjectRepository.findById(id).get();
    }

    public Stream<Subject> getAllFiles() {
        return subjectRepository.findAll().stream();
    }

    */

    public List<Favor> getMostPopularFavors(PageRequest pageRequest) {
        return favorRepository.getMostPopularFavors(pageRequest);
    }

    public List<Favor> findAll(){
        return favorRepository.findAll();
    }
}
