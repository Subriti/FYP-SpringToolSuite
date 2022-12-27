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
    public String updateUser(int userId, User Newuser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with ID " + userId + " does not exist"));

        if (Newuser.getUserName() != null && Newuser.getUserName().length() > 0
                && !Objects.equals(user.getUserName(), Newuser.getUserName())) {

            // checking if new username is available
            Optional<User> userOptional = userRepository.findUserByUsername(Newuser.getUserName());
            if (userOptional.isPresent()) {
                return "Username is already taken";
            }
            user.setUserName(Newuser.getUserName());
        }

        if (Newuser.getEmail() != null && Newuser.getEmail().length() > 0
                && !Objects.equals(user.getEmail(), Newuser.getEmail())) {
            // checking complexity of password
            if (PasswordValidator.EmailPatternMatches(Newuser.getEmail())) {
                user.setEmail(Newuser.getEmail());
            } else {
                return "Invalid Email";
            }
        }
        
        if (Newuser.getPassword() != null && Newuser.getPassword().length() > 0
                && !Objects.equals(user.getPassword(), Newuser.getPassword())) {
            // checking complexity of password
            if (PasswordValidator.isValid(Newuser.getPassword())) {
                
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(Newuser.getPassword());

                user.setPassword(hashedPassword);
                
            } else {
                return "Invalid Password";
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
        
        if (Newuser.getProfilePicture() != null && Newuser.getProfilePicture().length() > 0) {

            user.setProfilePicture(Newuser.getProfilePicture());
        }
        
        return "Successfully updated records";
    }

    @Transactional
    public String changePassword(int userId, String oldPassword, String newPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("user with id " + userId + " does not exist"));

        // storing last used passwords in a list
        List<String> list = userRepository.findPreviousPasswordsByID(userId);
        int length = list.size();
        int i = 0;


        if (oldPassword != null && oldPassword.length() > 0) {

            if (BCrypt.checkpw(oldPassword, user.getPassword())) {

                if (newPassword != null && newPassword.length() > 0 && !Objects.equals(oldPassword, newPassword)) {

                    if (PasswordValidator.isValid(newPassword)) {

                        while (i < length) {
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
                                // return "Successfully Updated the password record";
                            }
                            i++;
                        }
                        return "Successfully Updated the records";
                    }
                    return "New password doesn't meet required complexity definitions.\nRequired: minimum of 8characters, 1 numerical, 1 special character, 1capital letter and 1small letter";
                }
                return "New Password cannot be old password";
            }
            return "Incorrect Password";
        }
        return "Password Reset Failed";
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
                jsonObject.put("token", token);

                return jsonObject;
            }
        }
        jsonObject.put("error", "UserName or Password is invalid");

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
}
