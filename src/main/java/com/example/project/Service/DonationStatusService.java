package com.example.project.Service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.DonationStatus;
import com.example.project.Repository.DonationStatusRepository;

@Service
public class DonationStatusService {

	private final DonationStatusRepository donationStatusRepository;

	@Autowired
	public DonationStatusService(DonationStatusRepository donationStatusRepository) {
		this.donationStatusRepository = donationStatusRepository;
	}

	public List<DonationStatus> getDonationStatus() {
		return donationStatusRepository.findAll();
	}

	public void addNewDonationStatus(DonationStatus donationStatus) {
	    donationStatusRepository.save(donationStatus);
	}

	public void deleteDonationStatus(int donationStatusId) {
		boolean exists = donationStatusRepository.existsById(donationStatusId);
		if (!exists) {
			throw new IllegalStateException("status with id " + donationStatusId + " does not exist");
		}
		donationStatusRepository.deleteById(donationStatusId);
	}

	@Transactional
	public void updateDonationStatus(int donationStatusId, String donationStatusName) {
		DonationStatus donationStatus = donationStatusRepository.findById(donationStatusId)
				.orElseThrow(() -> new IllegalStateException("status with id " + donationStatusId + " does not exist"));

		if (donationStatusName != null && donationStatusName.length() > 0 && !Objects.equals(donationStatus.getDonationStatus(), donationStatusName)) {
		    donationStatus.setDonationStatus(donationStatusName);
		}
	}
}
