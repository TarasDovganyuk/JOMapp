package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.JomApplication;
import com.softserve.edu.jom.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = JomApplication.class)
public class UserRepositoryTest {
    TestEntityManager entityManager;

    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(TestEntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @Test
    public void testGetUserById() {
        User foundUser = userRepository.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("Alex", foundUser.getFirstName());
        assertEquals("Smith", foundUser.getLastName());
        assertEquals("alex@gmail.com", foundUser.getEmail());
        assertEquals("qweee1", foundUser.getPassword());
        assertEquals(User.Role.TRAINEE, foundUser.getRole());
    }

    @Test
    public void testGetUserWhenNotExist() {
        User foundUser = userRepository.getUserById(10L);
        assertNull(foundUser);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        String firstName = "Tumi";
        user.setFirstName(firstName);
        String lastName = "Multitumi";
        user.setLastName(lastName);
        User.Role role = User.Role.TRAINEE;
        user.setRole(role);
        String password = "Qwerty";
        user.setPassword(password);
        String email = "tumi@gmail.com";
        user.setEmail(email);
        User savedUser = userRepository.saveAndFlush(user);
        User foundUser = userRepository.getUserById(savedUser.getId());
        assertNotNull(foundUser);
        assertEquals(firstName, foundUser.getFirstName());
        assertEquals(lastName, foundUser.getLastName());
        assertEquals(email, foundUser.getEmail());
        assertEquals(password, foundUser.getPassword());
        assertEquals(role, foundUser.getRole());
    }

    @Test
    public void testSaveUserWithDuplicateEmail() {
        User user = userRepository.getUserById(1L);
        entityManager.detach(user);
        user.setId(null);
        user.setEmail("alex@gmail.com");
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(user);
        });
    }

    @Test
    public void testSaveUserWitInvalidEmail() {
        User user = userRepository.getUserById(1L);
        entityManager.detach(user);
        user.setId(null);
        user.setEmail("invalid_email");
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.saveAndFlush(user);
        });
    }

    @Test
    public void testSaveUserWitInvalidLastName() {
        User user = userRepository.getUserById(1L);
        entityManager.detach(user);
        user.setId(null);
        user.setEmail("new@gmail.com");
        user.setLastName("w");
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    public void testFindByRoleAndMarathon() {
        List<User> users = userRepository.findByRoleAndMarathonId(User.Role.MENTOR, 1L);
        assertEquals(1, users.size());
        assertEquals(4, users.get(0).getId());
        assertEquals("Victor", users.get(0).getFirstName());

        users = userRepository.findByRoleAndMarathonId(User.Role.TRAINEE, 1L);
        assertEquals(2, users.size());
    }
}