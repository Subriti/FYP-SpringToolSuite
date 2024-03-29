package com.example.project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.Post;
import com.example.project.Model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "SELECT * from report r where r.is_reviewed=false order by r.report_date DESC", nativeQuery = true)
    List<Report> getAll();

    @Query(value = "SELECT * from report r where r.post_id=?1", nativeQuery = true)
    List<Report> getReports(Post postId);
}
