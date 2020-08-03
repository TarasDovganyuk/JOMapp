package com.softserve.edu.jom.repository;

import com.softserve.edu.jom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getAllByRole(User.Role role);

    User getUserById(Long id);

    User getUserByIdAndRole(Long id, User.Role role);

    @Query("select user from User user join user.marathons maraphon where user.role =:role and maraphon.id =:marathonId")
    List<User> findByRoleAndMarathonId(@Param("role") User.Role role, @Param("marathonId") Long marathonId);

}
