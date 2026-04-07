package org.angelalfaro.gamevault.service;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // Method to get or create a user
    public User getOrCreateDefaultUser(String username) {
        return userRepository.findByUsernameUser(username)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsernameUser(username);
                    return userRepository.save(newUser);
                });
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
