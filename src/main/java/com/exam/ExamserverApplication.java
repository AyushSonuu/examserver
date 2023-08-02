package com.exam;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		 System.out.println("Starting Code");
//		 User user = new User();
//		 user.setFirstName("Ayush");
//		 user.setLastName("Ayush");
//		 user.setUserName("ayush29915");
//		 user.setPassword("29915");
//		 user.setEmail("sonuayush55@gmail.com");
//		 user.setProfile("default.png");
//
//		Role role1 = new Role();
//		role1.setRoleId(44l);
//		role1.setRoleName("ADMIN");
//
//		Set<UserRole> userRolesSet = new HashSet<>();
//		UserRole userRole = new UserRole();
//		userRole.setUser(user);
//		userRole.setRole(role1);
//		userRolesSet.add(userRole);
//
//		User user1 = this.userService.createUser(user, userRolesSet);
//		System.out.println(user1.getUserName());
		System.out.println(this.userService.getUser("ayush29915"));


	}
}
