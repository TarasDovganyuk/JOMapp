package com.softserve.edu.jom;

import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JomApplication implements CommandLineRunner {
    private UserService userService;

    @Autowired
    public JomApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JomApplication.class, args);
    }

    public void run(String... arg) throws Exception {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setPassword("qwertyqwert123");
            user.setRole(User.Role.MENTOR);
            user.setFirstName("MentorName" + i);
            user.setLastName("MentorSurname" + i);
            user.setEmail("mentoruser" + i + "@gmail.com");
            userService.createOrUpdateUser(user);

            User user1 = userService.getUserById(1L);
            System.out.println(user1);
        }
    }
}
