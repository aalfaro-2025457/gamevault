package org.angelalfaro.gamevault.controller;

import org.angelalfaro.gamevault.entity.Category;
import org.angelalfaro.gamevault.entity.Game;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.service.CategoryService;
import org.angelalfaro.gamevault.service.GameService;
import org.angelalfaro.gamevault.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = "http://localhost:5173") // Para que React (Vite) pueda conectar
public class GameRestController {

    private final GameService gameService;
    private final UserService userService;
    private final CategoryService categoryService;

    public GameRestController(GameService gameService, UserService userService, CategoryService categoryService) {
        this.gameService = gameService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    // Endpoint para que React busque y guarde un juego de Wikipedia
    @PostMapping("/import")
    public ResponseEntity<Game> importGame(@RequestParam String slug, @RequestParam String categoryName) {
        User user = userService.getOrCreateDefaultUser("Angel", "1234");
        Category category = categoryService.getOrCreateCategory(categoryName);

        Game savedGame = gameService.saveGame(slug, user, category);
        return ResponseEntity.ok(savedGame);
    }

    // Endpoint para el "Infinite Scroll" en React
    @GetMapping
    public ResponseEntity<Slice<Game>> getGamesApi(Pageable pageable) {
        User user = userService.getOrCreateDefaultUser("Angel", "1234");
        return ResponseEntity.ok(gameService.listUserGames(user.getIdUser(), pageable));
    }
}
