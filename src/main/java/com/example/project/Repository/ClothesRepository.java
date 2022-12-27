package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Clothes;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes,Integer> {

}
