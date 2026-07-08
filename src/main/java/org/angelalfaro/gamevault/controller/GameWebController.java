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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Integer id) {
        gameService.deleteGame(id);
        return "redirect:/admin/games";
    }

    // Load the edit form
    @PostMapping("/games/update/{id}")
    public String updateGame(@PathVariable Integer id,
                             @RequestParam String title,
                             @RequestParam String categoryName,
                             @RequestParam String imageUrl) {
        Game game = gameService.getGameById(id);
        game.setTitleGame(title);
        game.setImageUrlGame(imageUrl);
        game.setCategory(categoryService.getOrCreateCategory(categoryName));

        gameService.saveUpdatedGame(game);
        return "redirect:/admin/games";
    }

}