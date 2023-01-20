package com.example.project.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.project.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    //finding last used passwords from history table
    @Query(value = "SELECT p.changed_password FROM user_history p JOIN Users u on p.user_id = u.user_id WHERE u.user_id=?1 order by p.changed_date desc limit 3", nativeQuery=true)
    List<String> findPreviousPasswordsByID(int userId);
    
    @Query("SELECT u FROM User u WHERE u.email=?1")
    Optional<User> findUserByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.email=?1")
    User findByEmail(String email);
    
    @Query("SELECT u.password FROM User u WHERE u.email=?1")
    String findPasswordByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.userName=?1")
    Optional<User> findUserByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.userName=?1")
    User findByusername(String username);
}
    
	/*
	 * default User create(User user){ //user.setCreationDate(LocalDateTime.now());
	 * return this.save(user); }
	 *
	 * @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
	 * User findUserByStatusAndNameNamedParams(
	 *
	 * @Param("status") Integer status,
	 *
	 * @Param("name") String name);
	 */
    
    
    
    
    
    
    
    
    
    
    
    /*
    @Query(value = "SELECT p.changed_password FROM Password_History p JOIN Users u on p.user_id = u.user_id WHERE u.user_name=?1 order by p.changed_date desc limit 3", nativeQuery=true)
    List<String> findLastPasswordByUsername(String username);
    
		
		
		@Query("SELECT u FROM User u WHERE u.userName=?1")
		User findByusername(String username);
		
		//null aairacha
		@Query(value = "SELECT p.password FROM Users u JOIN Password_history p on p.user_id = u.user_id WHERE u.user_name=?1 order by p.date desc", nativeQuery=true)
		Optional<User> findLastPasswords(String username);
		
		//select PASSWORD, date from password_history where user_id=7 order by date desc limit 3

		
		
		@Query("SELECT u.password FROM User u WHERE u.userName=?1")
		String findPassword(String username);
		
		@Query("SELECT u.userId FROM User u WHERE u.userName=?1")
		int findUserId(String username);
		
		@Query("SELECT COALESCE(u.passwordExpiryMonths,'0') FROM User u WHERE u.userName=?1")
		int findPasswordExpiry(String username);
				
		@Query(value = "select p.date + ?1 *INTERVAL'1 month' from Users u JOIN Password_History p on p.user_id = u.user_id where p.user_id=?2 order by p.date desc LIMIT 1", nativeQuery = true)
		Date findExpiryDate(int passwordExpiryMonths, int userId);

		//@Query(value = "select u.password_expiry_months, p.date, p.date + CAST (u.password_expiry_months AS integer) *INTERVAL'1 month' \"EXPIRY DATE\"  from users u JOIN password_history p on p.user_id = u.user_id where p.user_id=7 order by date desc LIMIT 1", nativeQuery = true)
		
		
		 * @Query(value =
		 * "select CAST (u.password_expiry_months AS integer) *INTERVAL'1 month' from users u JOIN password_history p on p.user_id = u.user_id where p.user_id=?1 order by date desc LIMIT 1"
		 * , nativeQuery = true) Date findExpiryDate(int userId);
		 
}
*/