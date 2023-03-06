package com.example.project.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.User;
import com.example.project.Service.UserService;

import net.minidev.json.JSONObject;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/showUsers")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	// Single item
    @GetMapping(path= "/findUserByID/{userId}")
    public User findUserByID(@PathVariable int userId) {
        return userService.findUserByID(userId);
    }
    
 // Single item
    @GetMapping(path= "/findUserByName/{userName}")
    public User findUserByName(@PathVariable String userName) {
        return userService.findUserByName(userName);
    }
    
    @PostMapping(path= "/findUserByName")
    public User findUserByName(@RequestBody JSONObject userName) {
        return userService.findUserByName(userName);
    }
    
 // Single item
    @GetMapping(path= "/findUserByToken/{fcmToken}")
    public User findUserByToken(@PathVariable String fcmToken) {
        return userService.findUserByToken(fcmToken);
    }

	// Single item
	@GetMapping(path= "/findUser/{email}")
	public User findUserByEmail(@PathVariable String email) {
		return userService.findUserByEmail(email);
	}
	
	@GetMapping(path= "/findUserProfile/{email}")
    public User findUserProfile(@PathVariable String username) {
        return userService.findUserProfile(username);
    }
    
	
	@PostMapping(path= "/loginUser")
	public JSONObject loginUser(@RequestBody Map<String, Object> body) {
		return userService.Login(body.get("email").toString(),body.get("password").toString());
	}

	@PostMapping("/addUsers")
	public JSONObject addNewUser(@RequestBody User user) {
		return userService.addNewUser(user);
	}

	@DeleteMapping(path = "/deleteUsers/{userId}")
	public void deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
	}

	@PutMapping(path = "/updateUsers/{userId}")
	public JSONObject updateUser(@PathVariable int userId, @RequestBody User user) {
	    System.out.println(user);
		return userService.updateUser(userId, user);
	}
	
	@PostMapping(path= "/getFCMToken")
    public JSONObject getFCMToken(@RequestBody JSONObject userName) {
        return userService.getFCMToken(userName);
    }
	
	@PutMapping(path = "/updateFCMToken/{userId}")
    public JSONObject updateFCMToken(@PathVariable int userId, @RequestBody JSONObject FCMtoken) {
        System.out.println(FCMtoken);
        return userService.updateFCMtoken(userId, FCMtoken);
    }
	
	@PutMapping(path = "/resetPasswordChange/{userId}")
	public JSONObject resetPassword(@PathVariable int userId, @RequestParam(required = false) String user_name, @RequestParam(required = true) String old_password, @RequestParam(required = true) String new_password) {
		return userService.changePassword(userId, old_password, new_password);
	}
	
	@PutMapping(path = "/resetPassword/{userId}")
	public JSONObject requestPasswordChange(@PathVariable int userId, @RequestBody Map<String,Object> body) {
        return userService.changePassword(userId, body.get("old_password").toString(), body.get("new_password").toString());
	}
	
	@PutMapping(path = "/forgotPassword/{userId}")
	public String forgotPassword(@PathVariable int userId) {
        return userService.forgotPassword(userId);
	}
}
