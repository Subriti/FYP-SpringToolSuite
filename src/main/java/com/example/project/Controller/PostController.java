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

import com.example.project.Model.Post;
import com.example.project.Model.User;
import com.example.project.Service.PostService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/post")
public class PostController {

	private final PostService postService;

	@Autowired
	public PostController(PostService postService) {
        this.postService= postService;
    }

	@GetMapping("/showPosts")
	 public List<Post> getPost() {
        return postService.getPosts();
	}
	
	@GetMapping("/showUserPosts/{postBy}")
    public List<Post> getUserPost(@PathVariable("postBy") User userId) {
       return postService.getUserPosts(userId);
   }
	
	// Single item
    @GetMapping(path= "/findPost/{postId}")
    public Post findPost(@PathVariable int postId) {
        return postService.findPost(postId);
    }

    @PostMapping("/addPost")
    public JSONObject addNewPost(@RequestBody Post post) {
    	return postService.addNewPost(post);
	}

    @DeleteMapping(path= "/deletePost/{postId}")
    public void deletePost(@PathVariable("postId") int postId) {
    	postService.deletePost(postId);
    }

    @PutMapping(path = "/updatePost/{postId}")
    public void updatePost(@PathVariable int postId,@RequestBody Post post) {
    	postService.updatePost(postId,post);
    }
}
