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

import com.example.project.Model.DonationStatus;
import com.example.project.Service.DonationStatusService;

@RestController
@RequestMapping(path = "api/donationStatus")
public class DonationStatusController {

	private final DonationStatusService donationStatusService;

	@Autowired
	public DonationStatusController(DonationStatusService donationStatusService) {
        this.donationStatusService= donationStatusService;
    }

	@GetMapping("/showDonationStatus")
	 public List<DonationStatus> getDonationStatus() {
        return donationStatusService.getDonationStatus();
	}

    @PostMapping("/addDonationStatus")
    public void addNewDonationStatus(@RequestBody DonationStatus donationStatus) {
    	donationStatusService.addNewDonationStatus(donationStatus);
	}

    @DeleteMapping(path= "/deleteDonationStatus/{donation_status_id}")
    public void deleteDonationStatus(@PathVariable("donation_status_id") int donationStatusId) {
    	donationStatusService.deleteDonationStatus(donationStatusId);
    }

    /*
     * @PutMapping(path = "/updateDonationStatus/{donationStatusId}")
     * public void updateDonationStatus(@PathVariable("donationStatusId") int
     * donationStatusId, String donationStatus) {
     * donationStatusService.updateDonationStatus(donationStatusId,donationStatus);
     * }
     */
    
    //if updating from URL write the same variable name specified above i.e., 1? donationStatus= 
}
