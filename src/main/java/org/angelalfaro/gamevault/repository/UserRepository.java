package org.angelalfaro.gamevault.repository;

import org.angelalfaro.gamevault.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // To find a user by the username
    Optional<User> findByUsernameUser(String username);

}
