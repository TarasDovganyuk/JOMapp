package com.softserve.edu.jom.service;

import com.softserve.edu.jom.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@Transactional
public class UserServiceTest {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(2L);
        assertEquals("Mariana", user.getFirstName());
        assertEquals("Kuzma", user.getLastName());
        assertEquals("qweee2", user.getPassword());
        assertEquals(User.Role.TRAINEE, user.getRole());
        assertEquals("mari@gmail.com", user.getEmail());
    }
}