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
        User admin = userService.getOrCreateDefaultUser("Angel", "password123");
        Category rpg = categoryService.getOrCreateCategory("RPG");
        Category action = categoryService.getOrCreateCategory("Action");


        try {
            gameService.saveGame("The_Legend_of_Zelda:_Breath_of_the_Wild", admin, rpg,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVr96thWMbxyfSw-V8uUgv381QlsMLmyg04Q&s");
            gameService.saveGame("Elden_Ring", admin, rpg,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsyNmuTCej_baJF7_ZxwgBDXPgndwkI7r2mA&s");
            gameService.saveGame("God_of_War_Ragnarök", admin, action,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVq43klv5Q8ubFi2KHQMW7FilQrJpeClXN7g&s");
        } catch (Exception e) {
            System.out.println("Nota: No se pudieron cargar datos iniciales (posible firewall o error de API)");
        }

        System.out.println("¡Base de datos H2 lista y poblada!");
    }
}