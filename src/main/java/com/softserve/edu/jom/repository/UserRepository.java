package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> getAllByRole(User.Role role);
}
