package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.UserServiceException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.repository.MarathonRepository;
import com.softserve.edu.jom.repository.UserRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private MarathonRepository marathonRepository;

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        Collections.sort(users, Comparator.comparing(user -> user.getId()));
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Validate.notNull(id, "User id must not be null!");
        return userRepository.getUserById(id);
    }

    @Override
    public User createOrUpdateUser(User user) {
        if (user.getId() != null) {
            User existedUser = getUserById(user.getId());
            existedUser.setFirstName(user.getFirstName());
            existedUser.setLastName(user.getLastName());
            existedUser.setEmail(user.getEmail());
            existedUser.setRole(user.getRole());
            return userRepository.save(existedUser);
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllByRole(String role) throws UserServiceException {
        try {
            return userRepository.getAllByRole(User.Role.valueOf(role.toUpperCase()));
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllByRoleAndMarathonId(String role, Long marathonId) {
        try {
        return userRepository.findByRoleAndMarathonId(User.Role.valueOf(role.toUpperCase()), marathonId);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean addUserToMarathon(User user, Long marathonId) {
        try {
            Marathon existedMarathon = marathonRepository.getOne(marathonId);
            Validate.notNull(existedMarathon, "Marathon with id = %s is not found!", marathonId);
            existedMarathon.getUsers().add(user);
            return marathonRepository.save(existedMarathon) != null;
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean removeUserFromMarathon(Long userId, Long marathonId) {
        try {
            User user = getUserById(userId);
            Validate.notNull(user, "User with id = %s is not found!", userId);
            Marathon marathon = marathonRepository.getOne(marathonId);
            Validate.notNull(marathon, "Marathon with id = %s is not found!", marathonId);
            Set<User> newUserLinkedSet = marathon.getUsers().stream().filter(u -> u.getId() != userId).collect(Collectors.toSet());
            marathon.getUsers().clear();
            marathon.getUsers().addAll(newUserLinkedSet);
            marathonRepository.save(marathon);
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
        return true;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setMarathonRepository(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }
}
