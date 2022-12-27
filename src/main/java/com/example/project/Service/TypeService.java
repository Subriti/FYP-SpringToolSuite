package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Type;
import com.example.project.Repository.TypeRepository;

@Service
public class TypeService {

	private final TypeRepository TypeRepository;

	@Autowired
	public TypeService(TypeRepository TypeRepository) {
		this.TypeRepository = TypeRepository;
	}

	public List<Type> getType() {
		return TypeRepository.findAll();
	}

	public void addNewType(Type Type) {
	    TypeRepository.save(Type);
	}

	public void deleteType(int TypeId) {
		boolean exists = TypeRepository.existsById(TypeId);
		if (!exists) {
			throw new IllegalStateException("status with id " + TypeId + " does not exist");
		}
		TypeRepository.deleteById(TypeId);
	}

	@Transactional
	public void updateType(int TypeId, String TypeName) {
		Type Type = TypeRepository.findById(TypeId)
				.orElseThrow(() -> new IllegalStateException("status with id " + TypeId + " does not exist"));

		if (TypeName != null && TypeName.length() > 0 && !Objects.equals(Type.getTypeName(), TypeName)) {
		    Type.setTypeName(TypeName);
		}
	}
}
