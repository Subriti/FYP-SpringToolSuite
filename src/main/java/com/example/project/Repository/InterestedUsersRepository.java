package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.Model.InterestedUsers;
import com.example.project.Model.InterestedUsersPK;

@Repository
public interface InterestedUsersRepository extends JpaRepository<InterestedUsers,InterestedUsersPK> {

}
