package org.angelalfaro.gamevault.service;

import lombok.RequiredArgsConstructor;
import org.angelalfaro.gamevault.dto.WikipediaDTO;
import org.angelalfaro.gamevault.entity.Category;
import org.angelalfaro.gamevault.entity.Game;
import org.angelalfaro.gamevault.entity.User;
import org.angelalfaro.gamevault.repository.GameRepository;
import org.angelalfaro.gamevault.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final RestTemplate restTemplate;

    public Game saveGame(String slug, User user, Category category, String imageUrl) {
        Optional<Game> existing = gameRepository.findByWikiSlugAndUser(slug, user);
        if (existing.isPresent()) {
            return existing.get();
        }

        System.out.println(imageUrl);
        Game game = new Game();
        game.setTitleGame(slug.replace("_"," "));
        game.setWikiSlug(slug.replace(" ", "_"));
        game.setUser(user);
        game.setCategory(category);

        if (imageUrl != null && !imageUrl.isBlank()) {
            System.out.println(imageUrl);
            game.setImageUrlGame(imageUrl);
        } else {
            game.setImageUrlGame("https://img.freepik.com/vector-gratis/diseno-carteles-juegos-retro-dibujados-mano_23-2150852630.jpg?semt=ais_hybrid&w=740&q=80");
        }

        return gameRepository.save(game);
    }

    public Slice<Game> listUserGames(Integer userId, Pageable pageable) {
        return gameRepository.findByUser_IdUser(userId, pageable);
    }

    public void deleteGame(Integer id) {
        gameRepository.deleteById(id);
    }

    public Game getGameById(Integer id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Juego no encontrado"));
    }

    public Game saveUpdatedGame(Game game){
        return gameRepository.save(game);
    }

}
