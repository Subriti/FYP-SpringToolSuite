package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Clothes;
import com.example.project.Repository.ClothesRepository;

import net.minidev.json.JSONObject;

@Service
public class ClothesService {

    private final ClothesRepository clothesRepository;


    @Autowired
    public ClothesService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    public List<Clothes> getClothes() {
        return clothesRepository.findAll();
    }
    
    public Clothes findClothes(int clothesId) {
        return clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalStateException("Clothes with ID " + clothesId + " does not exist"));
    }

    public JSONObject addNewClothes(Clothes clothes) {
       clothesRepository.save(clothes);
       JSONObject jsonBody= new JSONObject();
       jsonBody.put("cloth_id",clothes.getClothId());
       System.out.println(jsonBody);
       return jsonBody;
       //return clothes.getClothId() or jsonBody.put("Cloth ID",clothes.getClothId());
       /*
        * Post post = new Post();
        * 
        * Clothes cloth = new Clothes();
        * cloth.setClothId(clothes.getClothId());
        * 
        * //post.setClothId(clothes);
        * post.setClothId(cloth);
        */
       //PostRepository.save(clothes);
       //return "Clothes Successfully Registered !!";
    }

    public void deleteClothes(int clothesId) {
        boolean exists = clothesRepository.existsById(clothesId);
        if (!exists) {
            throw new IllegalStateException("Clothes with ID " + clothesId + "does not exist");
        }
        clothesRepository.deleteById(clothesId);
    }

    @Transactional
    public String updateClothes(int clothesId, Clothes Newclothes) {
        Clothes clothes = clothesRepository.findById(clothesId)
                .orElseThrow(() -> new IllegalStateException("Clothes with ID " + clothesId + " does not exist"));
        
        if (Newclothes.getClothesCategoryId()!=null) {
            clothes.setClothesCategoryId(Newclothes.getClothesCategoryId());
        }
        
        if (Newclothes.getItemCategoryId()!=null) {
            clothes.setItemCategoryId(Newclothes.getItemCategoryId());
        }
        
        if (Newclothes.getClothSize()!=null && Newclothes.getClothSize().length() > 0) {
            clothes.setClothSize(Newclothes.getClothSize());
        }
        
        if (Newclothes.getClothCondition()!=null && Newclothes.getClothCondition().length() > 0) {
            clothes.setClothCondition(Newclothes.getClothCondition());
        }
        
        if (Newclothes.getClothSeason()!=null && Newclothes.getClothSeason().length() > 0) {
            clothes.setClothSeason(Newclothes.getClothSeason());
        }
     
        return "Successfully updated records";
    }

}
