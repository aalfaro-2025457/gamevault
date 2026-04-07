package org.angelalfaro.gamevault.controller;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.entity.Category;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.service.CategoryService;
import org.angelalfaro.gamevault.service.GameService;
import org.angelalfaro.gamevault.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final GameService gameService;

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario y categorías base
        User admin = userService.getOrCreateDefaultUser("Angel", "password123");
        Category rpg = categoryService.getOrCreateCategory("RPG");
        Category action = categoryService.getOrCreateCategory("Action");

        // Importar un par de juegos de Wikipedia automáticamente
        try {
            gameService.saveGame("The_Legend_of_Zelda:_Breath_of_the_Wild", admin, rpg);
            gameService.saveGame("Elden_Ring", admin, rpg);
            gameService.saveGame("God_of_War_Ragnarök", admin, action);
        } catch (Exception e) {
            System.out.println("Nota: No se pudieron cargar datos iniciales (posible firewall o error de API)");
        }

        System.out.println("¡Base de datos H2 lista y poblada!");
    }
}