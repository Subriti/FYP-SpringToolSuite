package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.InterestedUsers;
import com.example.project.Service.InterestedUsersService;

@RestController
@RequestMapping(path = "api/interestedUsers")
public class InterestedUsersController {

	private final InterestedUsersService InterestedUsersService;

	@Autowired
	public InterestedUsersController(InterestedUsersService InterestedUsersService) {
        this.InterestedUsersService= InterestedUsersService;
    }

	@GetMapping("/showInterestedUsers")
	 public List<InterestedUsers> getInterestedUsers() {
        return InterestedUsersService.getInterestedUsers();
	}

    @PostMapping("/addInterestedUsers")
    public void addNewInterestedUsers(@RequestBody InterestedUsers InterestedUsers) {
    	InterestedUsersService.addNewInterestedUsers(InterestedUsers);
	}

    /*
     * @DeleteMapping(path= "/deleteInterestedUsers/{InterestedUsersId}")
     * public void deleteInterestedUsers(@PathVariable("InterestedUsersId") int
     * InterestedUsersId) {
     * InterestedUsersService.deleteInterestedUsers(InterestedUsersId);
     * }
     */
}
