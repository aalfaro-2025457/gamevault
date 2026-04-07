package org.angelalfaro.gamevault.repository;

import org.angelalfaro.gamevault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // To find a user by the username
    Optional<User> findByUsernameUser(String username);

}
