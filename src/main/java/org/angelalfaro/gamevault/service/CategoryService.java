package org.angelalfaro.gamevault.service;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.entity.Category;
import org.angelalfaro.gamevault.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getOrCreateCategory(String name) {
        return categoryRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    Category newCat = new Category();
                    newCat.setName(name);
                    return categoryRepository.save(newCat);
                });
    }
}
