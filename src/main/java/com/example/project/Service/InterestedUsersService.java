package com.example.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.InterestedUsers;
import com.example.project.Repository.InterestedUsersRepository;

@Service
public class InterestedUsersService {

	private final InterestedUsersRepository InterestedUsersRepository;

	@Autowired
	public InterestedUsersService(InterestedUsersRepository InterestedUsersRepository) {
		this.InterestedUsersRepository = InterestedUsersRepository;
	}

	public List<InterestedUsers> getInterestedUsers() {
		return InterestedUsersRepository.findAll();
	}

	public void addNewInterestedUsers(InterestedUsers InterestedUsers) {
	    InterestedUsersRepository.save(InterestedUsers);
	}

    /*
     * public void deleteInterestedUsers(int InterestedUsersId) {
     * boolean exists = InterestedUsersRepository.existsById(InterestedUsersId);
     * if (!exists) {
     * throw new IllegalStateException("status with id " + InterestedUsersId +
     * " does not exist");
     * }
     * InterestedUsersRepository.deleteById(InterestedUsersId);
     * }
     */
}
