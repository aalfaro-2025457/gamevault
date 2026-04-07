package org.angelalfaro.gamevault.controller;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.entity.Game;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.service.CategoryService;
import org.angelalfaro.gamevault.service.GameService;
import org.angelalfaro.gamevault.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class GameWebController {

    private final GameService gameService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("/games")
    public String listGames(Model model, Pageable pageable) {
        // Obtenemos un usuario por defecto para las pruebas
        User defaultUser = userService.getOrCreateDefaultUser("Angel", "1234");

        // Usamos Slice para la lista
        Slice<Game> gameSlice = gameService.listUserGames(defaultUser.getIdUser(), pageable);

        model.addAttribute("games", gameSlice.getContent());
        model.addAttribute("hasNext", gameSlice.hasNext());
        return "games-list"; // Search src/main/resources/templates/games-list.html
    }
}