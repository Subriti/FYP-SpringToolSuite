package com.example.project.Service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.InterestedUsers;
import com.example.project.Model.Post;
import com.example.project.Model.User;
import com.example.project.Repository.InterestedUsersRepository;

import net.bytebuddy.asm.Advice.Return;
import net.minidev.json.JSONObject;

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

    public JSONObject addNewInterestedUsers(InterestedUsers InterestedUsers) {
        InterestedUsers user = InterestedUsersRepository.findUser(InterestedUsers.getPostId(), InterestedUsers.getUserId());
        JSONObject jsonBody = new JSONObject();
        if (user==null) {
            InterestedUsersRepository.save(InterestedUsers);
            jsonBody.put("Success", "Interested User added");
            return jsonBody;
        }
        jsonBody.clear();
        jsonBody.put("Error", "Interested User already exists");
        return jsonBody;
    }

    public List<InterestedUsers> findUsersByPost(Post postId) {
        return InterestedUsersRepository.findUserByPost(postId);
    }

    public void deleteInterestedUsers(Post postId, User userId) {
        InterestedUsers user = InterestedUsersRepository.findUser(postId, userId);
       System.out.println(user.getUserId());
        if (user!=null) {
            //InterestedUsersRepository.DeleteLike(postId, userId);
            InterestedUsersRepository.deleteById(user.getInterestedId());
        }
        /*
         * else {
         * throw new IllegalStateException("Interested User with ID " + userId +
         * "for post "+postId +" does not exist");
         * }
         */
    }
    
    public JSONObject deleteAllInterestedUsers(Post postId) {
        List<InterestedUsers> user = InterestedUsersRepository.findUserByPost(postId); 
        JSONObject jsonBody = new JSONObject();
        if (user!=null) {
            InterestedUsersRepository.DeletePostLikes(postId);
            jsonBody.put("Success", "Interested Users deleted");
            return jsonBody;
        }
        jsonBody.clear();
        jsonBody.put("Error", "Could not delete interested users");
        return jsonBody;
    }

}
