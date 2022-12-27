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

import com.example.project.Model.Type;
import com.example.project.Service.TypeService;

@RestController
@RequestMapping(path = "api/type")
public class TypeController {

	private final TypeService TypeService;

	@Autowired
	public TypeController(TypeService TypeService) {
        this.TypeService= TypeService;
    }

	@GetMapping("/showType")
	 public List<Type> getType() {
        return TypeService.getType();
	}

    @PostMapping("/addType")
    public void addNewType(@RequestBody Type Type) {
    	TypeService.addNewType(Type);
	}

    @DeleteMapping(path= "/deleteType/{TypeId}")
    public void deleteType(@PathVariable("TypeId") int TypeId) {
    	TypeService.deleteType(TypeId);
    }

    @PutMapping(path = "/updateType/{TypeId}")
    public void updateType(@PathVariable("TypeId") int TypeId, String TypeName) {
    	TypeService.updateType(TypeId,TypeName);
    }
}
