package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.UserHistory;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory,Integer> {

}
