package com.softserve.edu.jom.service;

import com.softserve.edu.jom.exception.UserServiceException;
import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUserById(Long id);

    User createOrUpdateUser(User user);

    List<User> getAllByRole(String role) throws UserServiceException;

    List<User> getAllByRoleAndMarathonId(String role, Long marathonId) throws UserServiceException;

    boolean addUserToMarathon(User user,  Long marathonId);

    boolean removeUserFromMarathon(Long userId, Long marathonId);
}
