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
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private MarathonRepository marathonRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Validate.notNull(id, "User id must not be null!");
        return userRepository.getUserById(id);
    }

    @Override
    public User createOrUpdateUser(User user) {
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
    public boolean addUserToMarathon(User user, Marathon marathon) {
        try {
            Marathon existedMarathon = marathonRepository.getOne(marathon.getId());
            Validate.notNull(existedMarathon, "Marathon with id = %s is not found!", marathon.getId());
            User existedUser = userRepository.getOne(user.getId());
            Validate.notNull(existedUser, "User with id = %s is not found!", user.getId());
            existedMarathon.getUsers().add(user);
            return marathonRepository.save(existedMarathon) != null;
        } catch (Exception e) {
            throw new UserServiceException(e.getMessage(), e);
        }
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
