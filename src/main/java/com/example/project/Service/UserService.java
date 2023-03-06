package com.example.project.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.PasswordGenerator;
import com.example.project.PasswordValidator;
import com.example.project.Model.User;
import com.example.project.Model.UserHistory;
import com.example.project.Repository.UserHistoryRepository;
import com.example.project.Repository.UserRepository;
import com.example.project.utility.JWTUtility;

import net.minidev.json.JSONObject;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserHistoryRepository passwordHistoryRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserHistoryRepository passwordHistoryRepository) {
        this.userRepository = userRepository;
        this.passwordHistoryRepository = passwordHistoryRepository;
    }

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findUserByEmail(String email) {
        User user= userRepository.findByEmail(email);
        if (user==null) {
            throw new IllegalStateException("User with Email " + email + "does not exist");
        }
       return user;
    }
    
    public User findUserByID(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));
        return user;
    }
    
    public User findUserByName(String userName) {
        User user = userRepository.findByName(userName);
        return user;
    }
    
    public User findUserByName(JSONObject name) {
        JSONObject tokenString= new JSONObject(name);
        String userName= tokenString.getAsString("user_name");
        
        User user = userRepository.findByusername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        return user;
    }
    
    public User findUserByToken(String fcmToken) {
        User user = userRepository.findByFCMtoken(fcmToken);
        return user;
    }
   
    public User findUserProfile(String username) {
        User user= userRepository.findByusername(username);
        if (user==null) {
            throw new IllegalStateException("User with username " + username + "does not exist");
        }
       return user;
    }

    public JSONObject addNewUser(User user) {
        JSONObject jsonObject= new JSONObject();
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUserName());
        System.out.println(userOptional);
        if (userOptional.isPresent()) {
            jsonObject.clear();
            jsonObject.put("Error message", "Username is already taken");
            return jsonObject;
        }

        String hashedPassword = "";
        String password = user.getPassword();

        // checking if password abides by the regex with digits and capital letters
        if (PasswordValidator.isValid(password)) {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashedPassword = passwordEncoder.encode(password);

            user.setPassword(hashedPassword);

            userRepository.save(user);

            // storing the passwords to restrict the use of previously used passwords
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate currentDate = LocalDate.now();
            Date endDate = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());

            UserHistory passwordHistory = new UserHistory();

            User userNew = new User();
            userNew.setUserId(user.getUserId());

            passwordHistory.setUserId(userNew);
            passwordHistory.setChangedDate(endDate);
            passwordHistory.setChangedPassword(hashedPassword);
            passwordHistoryRepository.save(passwordHistory);

            jsonObject.clear();
            jsonObject.put("Success message", "User Successfully Registered !!");
            jsonObject.put("user_id", user.getUserId());
            jsonObject.put("user_name", user.getUserName());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("birth_date", user.getBirth_date());
            jsonObject.put("location", user.getLocation());
            jsonObject.put("phone_number", user.getPhoneNumber());
            jsonObject.put("profile_picture", user.getProfilePicture());
            return jsonObject;
        }
        jsonObject.clear();
        jsonObject.put("Error message", "New password doesn't meet required complexity definitions. \\nRequired: minimum of 8characters, 1 numerical, 1 special character, 1capital letter and 1small letter");
        return jsonObject;
    }

    public void deleteUser(int userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with ID " + userId + "does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public JSONObject updateUser(int userId, User Newuser) {
        String token = "token";
        JSONObject jsonObject = new JSONObject();
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));

        if (Newuser.getUserName() != null && Newuser.getUserName().length() > 0
                && !Objects.equals(user.getUserName(), Newuser.getUserName())) {

            // checking if new username is available
            Optional<User> userOptional = userRepository.findUserByUsername(Newuser.getUserName());
            if (userOptional.isPresent()) {
                jsonObject.clear();
                jsonObject.put("Error Message", "Username is already taken");
                return jsonObject;
            }
            user.setUserName(Newuser.getUserName());

            UserDetails userDetails = loadUserByUsername(Newuser.getUserName());
            token = jwtUtility.generateToken(userDetails);
        }

        if (Newuser.getEmail() != null && Newuser.getEmail().length() > 0
                && !Objects.equals(user.getEmail(), Newuser.getEmail())) {
            // checking complexity of password
            if (PasswordValidator.EmailPatternMatches(Newuser.getEmail())) {
                user.setEmail(Newuser.getEmail());
            } else {
                jsonObject.clear();
                jsonObject.put("Error Message",  "Invalid Email");
                return jsonObject;
            }
        }
        
      
        if (Newuser.getBirth_date() != null && !Objects.equals(user.getBirth_date(), Newuser.getBirth_date())) {
            user.setBirth_date(Newuser.getBirth_date());
        }

        if (Newuser.getPhoneNumber() != null && Newuser.getPhoneNumber().length() > 0
                && Newuser.getPhoneNumber().length() < 15
                && !Objects.equals(user.getPhoneNumber(), Newuser.getPhoneNumber())) {

            user.setPhoneNumber(Newuser.getPhoneNumber());
        }

        if (Newuser.getLocation() != null && Newuser.getLocation().length() > 0
                && !Objects.equals(user.getLocation(), Newuser.getLocation())) {

            user.setLocation(Newuser.getLocation());
        }
        
        if (Newuser.getProfilePicture() != null && Newuser.getProfilePicture().length() > 0 && !Objects.equals(user.getProfilePicture(), Newuser.getProfilePicture())) {

            user.setProfilePicture(Newuser.getProfilePicture());
        }
        jsonObject.clear();
        jsonObject.put("Message", "Successfully Updated the records");
        jsonObject.put("user_id", user.getUserId());
        jsonObject.put("user_name", user.getUserName());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("birth_date", user.getBirth_date());
        jsonObject.put("location", user.getLocation());
        jsonObject.put("phone_number", user.getPhoneNumber());
        jsonObject.put("profile_picture", user.getProfilePicture());
        jsonObject.put("token", token);
        return jsonObject;
    }

    @Transactional
    public JSONObject changePassword(int userId, String oldPassword, String newPassword) {

        JSONObject jsonObject = new JSONObject();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist"));

        // storing last used passwords in a list
        List<String> list = userRepository.findPreviousPasswordsByID(userId);
        int i = 0;


        if (oldPassword != null && oldPassword.length() > 0) {

            if (BCrypt.checkpw(oldPassword, user.getPassword())) {

                if (newPassword != null && newPassword.length() > 0 && !Objects.equals(oldPassword, newPassword)) {

                    if (PasswordValidator.isValid(newPassword)) {

                        while (i < list.size()) {
                            if (!BCrypt.checkpw(newPassword, list.get(i))) {

                                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                                String hashedPassword = passwordEncoder.encode(newPassword);

                                user.setPassword(hashedPassword);


                                // saving the password in history table
                                ZoneId defaultZoneId = ZoneId.systemDefault();
                                LocalDate currentDate = LocalDate.now();
                                Date endDate = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());

                                UserHistory passwordHistory = new UserHistory();

                                User userNew = new User();
                                userNew.setUserId(userId);

                                passwordHistory.setUserId(userNew);
                                passwordHistory.setChangedDate(endDate);
                                passwordHistory.setChangedPassword(hashedPassword);
                                passwordHistoryRepository.save(passwordHistory);
                            }
                            i++;
                        }
                        jsonObject.clear();
                        jsonObject.put("Message", "Successfully Updated the records");
                        return jsonObject;
                    }
                    jsonObject.clear();
                    jsonObject.put("Error", "New password doesn't meet required complexity definitions.\nRequired: minimum of 8characters, 1 numerical, 1 special character, 1capital letter and 1small letter");
                    return jsonObject;
                }
                jsonObject.clear();
                jsonObject.put("Error","New Password cannot be old password");
                return jsonObject;
            }
            jsonObject.clear();
            jsonObject.put("Error","Incorrect Password");
            return jsonObject;
        }
        jsonObject.clear();
        jsonObject.put("Error","Password Reset Failed");
        return jsonObject;
    }


    @Transactional
    public String forgotPassword(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist"));

        String generatedPassword = PasswordGenerator.generateStrongPassword();
        String newPassword = PasswordGenerator.shuffleString(generatedPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(hashedPassword);

        return "Successful !! Your new password is " + hashedPassword;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Transactional
    public JSONObject Login(String email, String password) {
        JSONObject jsonObject = new JSONObject();

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            // If the email is valid --> then login
            String passwordString = userRepository.findPasswordByEmail(email);
            System.out.println(passwordString);


            if (BCrypt.checkpw(password, passwordString)) {

                User user = userRepository.findByEmail(email);

                try {
                    authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), passwordString));
                } catch (BadCredentialsException e) {
                    jsonObject.put("error", "INVALID CREDENTIALS " + e);
                    return jsonObject;
                }

                UserDetails userDetails = loadUserByUsername(user.getUserName());
                final String token = jwtUtility.generateToken(userDetails);


                jsonObject.put("message", "Successful Login !! ");

                jsonObject.put("user_id", user.getUserId());
                jsonObject.put("user_name", user.getUserName());
                jsonObject.put("email", user.getEmail());
                jsonObject.put("birth_date", user.getBirth_date());
                jsonObject.put("location", user.getLocation());
                jsonObject.put("phone_number", user.getPhoneNumber());
                jsonObject.put("profile_picture", user.getProfilePicture());
                jsonObject.put("token", token);

                System.out.println(jsonObject);
                return jsonObject;
            }
        }
        jsonObject.put("error", "UserName or Password is invalid");
        System.out.println("UserName or Password is invalid");

        return jsonObject;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByusername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }

        String name = user.getUserName();
        String password = user.getPassword();
        return new org.springframework.security.core.userdetails.User(name, password, new ArrayList<>());
    }
    
    @Transactional
    public JSONObject updateFCMtoken(int userId, JSONObject FCMtoken) {
        JSONObject jsonObject = new JSONObject();
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));
        
        JSONObject tokenString= new JSONObject(FCMtoken);
        String token= tokenString.getAsString("fcm_token");

        if (token != null && token.length() > 0 && !Objects.equals(user.getFCMtoken(), token)) {
            user.setFCMtoken(token);
        }
        jsonObject.clear();
        jsonObject.put("Message", "Successfully Updated the fcm Token");
        jsonObject.put("user_id", user.getUserId());
        jsonObject.put("user_name", user.getUserName());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("birth_date", user.getBirth_date());
        jsonObject.put("location", user.getLocation());
        jsonObject.put("phone_number", user.getPhoneNumber());
        jsonObject.put("profile_picture", user.getProfilePicture());
        jsonObject.put("fcm_token", token);
        return jsonObject;
    }

    public JSONObject getFCMToken(JSONObject name) {
        JSONObject jsonObject = new JSONObject();
       
        JSONObject tokenString= new JSONObject(name);
        String userName= tokenString.getAsString("user_name");
        
        User user = userRepository.findByusername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        jsonObject.put("fcm_token", user.getFCMtoken());
        return jsonObject;
    }
}
