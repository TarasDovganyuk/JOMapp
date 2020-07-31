package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.UserServiceException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.repository.MarathonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@Transactional
public class UserServiceTest {
    private UserService userService;
    private MarathonRepository marathonRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMarathonRepository(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
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

    @Test
    public void testGetAll() {
        List<User> userList = userService.getAll();
        assertEquals(5, userList.size());
    }

    @Test
    public void testCreateUser() {
        User user = createNewUser();
        User savedUser = userService.createOrUpdateUser(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(user.getFirstName(), savedUser.getFirstName());
        assertEquals(user.getLastName(), savedUser.getLastName());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getRole(), savedUser.getRole());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        String newName = "Vladlen";
        Long userId = 1L;
        User user = userService.getUserById(userId);
        user.setFirstName(newName);
        userService.createOrUpdateUser(user);
        User savedUser = userService.getUserById(userId);
        assertEquals(newName, savedUser.getFirstName());
    }


    @Test
    public void testGetAllByRole() {
        List<User> mentorList = userService.getAllByRole("MENTOR");
        assertEquals(2, mentorList.size());
    }

    @Test
    public void testGetAllByRoleWhenRoleWrong() {
        assertThrows(UserServiceException.class, () -> {
            userService.getAllByRole("wrong_role");
        });
    }

    @Test
    public void testAddUserToNonExistingMarathon() {
        assertThrows(UserServiceException.class, () -> {
            userService.addUserToMarathon(createNewUser(), 1000L);
        });
    }

    @Test
    public void testRemoveUserFromNonExistingMarathon() {
        assertThrows(UserServiceException.class, () -> {
            userService.removeUserFromMarathon(1L, 1000L);
        });
    }

    @Test
    public void testRemoveNonExistingUserFromMarathon() {
        assertThrows(UserServiceException.class, () -> {
            userService.removeUserFromMarathon(1000L, 1L);
        });
    }

    @Test
    public void testGetAllByRoleAndMarathonId() {
        List<User> mentorList = userService.getAllByRoleAndMarathonId("MENTOR", 2L);
        assertEquals(1, mentorList.size());
        assertEquals("Gari", mentorList.get(0).getFirstName());
        assertEquals("Curl", mentorList.get(0).getLastName());
        assertEquals("qweee", mentorList.get(0).getPassword());
        assertEquals(User.Role.MENTOR, mentorList.get(0).getRole());
        assertEquals("gari@gmail.com", mentorList.get(0).getEmail());
    }

    @Test
    public void testAddNewUserToMarathon() {
        User user = createNewUser();
        Long marathonId = 1L;
        boolean isAdded = userService.addUserToMarathon(user, marathonId);
        assertTrue(isAdded);
        Marathon marathon = marathonRepository.getMarathonById(marathonId);
        Set<User> userList = marathon.getUsers();
        assertTrue(userList != null && userList.size() >= 1);
        User savedUser = userList.stream().filter(i -> "alex.smith@gmail.com" .equals(i.getEmail())).collect(Collectors.toList()).get(0);
        assertEquals("Alex", savedUser.getFirstName());
        assertEquals("Smith", savedUser.getLastName());
        assertEquals("asdasdsd", savedUser.getPassword());
        assertEquals(User.Role.MENTOR, savedUser.getRole());
        assertEquals("alex.smith@gmail.com", savedUser.getEmail());
    }

    @Test
    public void testRemoveUserToMarathon() {
        Long userId = 1L;
        Long marathonId = 1L;
        int initialUserMarathonCount = userService.getUserById(userId).getMarathons().size();
        userService.removeUserFromMarathon(userId, marathonId);
        int currentUserMarathonCount = userService.getUserById(userId).getMarathons().size();
        assertEquals(initialUserMarathonCount, currentUserMarathonCount);
    }

    private User createNewUser() {
        User user = new User();
        user.setFirstName("Alex");
        user.setLastName("Smith");
        user.setEmail("alex.smith@gmail.com");
        user.setPassword("asdasdsd");
        user.setRole(User.Role.MENTOR);
        return user;
    }
}