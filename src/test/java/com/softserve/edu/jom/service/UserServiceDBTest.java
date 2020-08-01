package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.UserServiceException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@Transactional
@AutoConfigureTestEntityManager
public class UserServiceDBTest {
    private UserService userService;
    private TestEntityManager entityManager;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEntityManager(TestEntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void testAddNewUserToMarathon() {
        User user = new User();
        String fisrtName = "Alex";
        user.setFirstName(fisrtName);
        String lastName = "Smith";
        user.setLastName(lastName);
        String emai = "alex.smith@gmail.com";
        user.setEmail(emai);
        String password = "asdasdsd";
        user.setPassword(password);
        User.Role role = User.Role.MENTOR;
        user.setRole(role);
        Long marathonId = 1L;
        boolean isAdded = userService.addUserToMarathon(user, marathonId);
        assertTrue(isAdded);
        Marathon marathon = entityManager.find(Marathon.class, marathonId);
        Set<User> userList = marathon.getUsers();
        assertTrue(userList != null && userList.size() >= 1);
        User savedUser = userList.stream().filter(i -> emai.equals(i.getEmail())).collect(Collectors.toList()).get(0);
        assertEquals(fisrtName, savedUser.getFirstName());
        assertEquals(lastName, savedUser.getLastName());
        assertEquals(password, savedUser.getPassword());
        assertEquals(role, savedUser.getRole());
        assertEquals(emai, savedUser.getEmail());
    }

    @Test
    public void testRemoveUserFromMarathon() {
        Long userId = 1L;
        Long marathonId = 1L;
        int initialUserMarathonCount = userService.getUserById(userId).getMarathons().size();
        userService.removeUserFromMarathon(userId, marathonId);
        int currentUserMarathonCount = userService.getUserById(userId).getMarathons().size();
        assertEquals(initialUserMarathonCount, currentUserMarathonCount);
    }
}