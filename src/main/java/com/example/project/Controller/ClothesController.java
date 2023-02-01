package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Clothes;
import com.example.project.Service.ClothesService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/clothes")
public class ClothesController {

	private final ClothesService ClothesService;

	@Autowired
	public ClothesController(ClothesService ClothesService) {
        this.ClothesService= ClothesService;
    }

	@GetMapping("/showClothes")
	 public List<Clothes> getClothes() {
        return ClothesService.getClothes();
	}

    @PostMapping("/addClothes")
    public JSONObject addNewClothes(@RequestBody Clothes Clothes) {
    	return ClothesService.addNewClothes(Clothes);
	}

    @DeleteMapping(path= "/deleteClothes/{ClothesId}")
    public void deleteClothes(@PathVariable("ClothesId") int ClothesId) {
    	ClothesService.deleteClothes(ClothesId);
    }

    @PutMapping(path = "/updateClothes/{ClothesId}")
    public JSONObject updateClothes(@PathVariable("ClothesId") int ClothesId, @RequestBody Clothes newCloth) {
    	return ClothesService.updateClothes(ClothesId,newCloth);
    }
}
