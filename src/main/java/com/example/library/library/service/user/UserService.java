package com.example.library.library.service.user;

import com.example.library.library.model.user.Role;
import com.example.library.library.model.user.User;
import com.example.library.library.registration.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService{

    List<User> getAllUsers();

    List<Role> getAllRoles();

    User createNewUser(User user);

    User updateUser(User user);

    User getUserById(Long pid);

    Optional<User> getUserByLogin(String login);

    void deleteUserById(Long pid);
    
    List<User> filterUser(String filterText);

    User save(UserRegistrationDto registration);


}
