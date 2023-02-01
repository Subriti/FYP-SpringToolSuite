package com.example.project.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.InterestedUsers;
import com.example.project.Model.Post;
import com.example.project.Model.User;
import com.example.project.Service.InterestedUsersService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/interestedUsers")
public class InterestedUsersController {

    private final InterestedUsersService InterestedUsersService;

    @Autowired
    public InterestedUsersController(InterestedUsersService InterestedUsersService) {
        this.InterestedUsersService = InterestedUsersService;
    }

    @GetMapping("/showInterestedUsers")
    public List<InterestedUsers> getInterestedUsers() {
        return InterestedUsersService.getInterestedUsers();
    }

    @GetMapping("/getUsersByPost/{postId}")
    public List<InterestedUsers> getUserByPost(@PathVariable("postId") Post postId) {
        return InterestedUsersService.findUsersByPost(postId);
    }

    @PostMapping("/addInterestedUsers")
    public JSONObject addNewInterestedUsers(@RequestBody InterestedUsers InterestedUsers) {
        return InterestedUsersService.addNewInterestedUsers(InterestedUsers);
    }

    @DeleteMapping(path= "/deleteInterestedUsers/{postId}/{userId}")
    public void deleteInterestedUsers(@PathVariable Post postId, @PathVariable User userId) {
        InterestedUsersService.deleteInterestedUsers(postId,userId);
    }   
}
