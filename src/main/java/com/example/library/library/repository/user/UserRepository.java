package com.example.library.library.repository.user;

import com.example.library.library.model.user.Role;
import com.example.library.library.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByLogin(String login);

    @Query("select r from Role r")
    List<Role> getAllRolesForUser();

    User findUserByLogin(String login);
}
