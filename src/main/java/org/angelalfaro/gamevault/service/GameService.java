package org.angelalfaro.gamevault.service;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.dto.WikipediaDTO;
import org.angelalfaro.gamevault.entity.Category;
import org.angelalfaro.gamevault.entity.Game;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.repository.CategoryRepository;
import org.angelalfaro.gamevault.repository.GameRepository;
import org.angelalfaro.gamevault.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final RestTemplate restTemplate;
    private final String WIKI_API_URL = "https://en.wikipedia.org/api/rest_v1/page/summary/";

    public Game saveGame(String slug, User user, Category category) {
        Game game = new Game();
        game.setTitleGame(slug.replace("_", " "));
        game.setWikiSlug(slug);
        game.setUser(user);
        game.setCategory(category);
        // Agregamos un bloque try por si Wikipedia falla, que no muera el server
        try {
            // ... lógica de restTemplate ...
        } catch (Exception e) {
            System.out.println("Error Wikipedia, pero guardamos igual.");
        }
        return gameRepository.save(game);
    }

    public Slice<Game> listUserGames(Integer userId, Pageable pageable) {
        return gameRepository.findByUser_IdUser(userId, pageable);
    }

}
