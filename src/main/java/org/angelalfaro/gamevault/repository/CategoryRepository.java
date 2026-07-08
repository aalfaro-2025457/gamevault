package org.angelalfaro.gamevault.repository;

import org.angelalfaro.gamevault.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // To validate if a category exists
    Optional<Category> findByNameIgnoreCase(String name);

}
