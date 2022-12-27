package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {

}
