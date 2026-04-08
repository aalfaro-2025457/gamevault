package org.angelalfaro.gamevault.controller;

import lombok.RequiredArgsConstructor;

import org.angelalfaro.gamevault.entity.Category;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class GameWebController {

    private final GameService gameService;
    private final UserService userService;
    private final CategoryService categoryService;

    // Endpoint to get the lis of games
    @GetMapping("/games")
    public String listGames(Model model, Pageable pageable) {
        User defaultUser = userService.getOrCreateDefaultUser("Angel", "1234");
        Slice<Game> gameSlice = gameService.listUserGames(defaultUser.getIdUser(), pageable);

        model.addAttribute("games", gameSlice.getContent());
        model.addAttribute("hasNext", gameSlice.hasNext());
        model.addAttribute("currentPage", pageable.getPageNumber());
        return "games-list"; 
    }

    // end point to use in the form to add a new game
    @PostMapping("/games/add")
    public String addGame(@RequestParam String title,
                          @RequestParam String categoryName,
                          @RequestParam String imageUrl) {
        
        User user = userService.getOrCreateDefaultUser("Angel", "1234");
        Category category = categoryService.getOrCreateCategory(categoryName);
        
        gameService.saveGame(title, user, category, imageUrl);
        
        return "redirect:/admin/games";
    }
}