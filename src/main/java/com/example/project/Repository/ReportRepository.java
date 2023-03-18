package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Clothes;
import com.example.project.Model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

}
