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

    public Game saveGame(String slug, Integer iduUser, String category){

        try {
            // Use the api
            String url = WIKI_API_URL + slug;
            WikipediaDTO response = restTemplate.getForObject(url, WikipediaDTO.class);

            if (response == null || response.title() == null){
                throw new RuntimeException("Didn't find information for: " + slug);
            }

            Game game = new Game();
            game.setTitleGame(response.title());
            game.setWikiSlug(slug);

            if (response.originalimage() != null) {
                game.setImageUrlGame(response.originalimage().getSource());
            }

            game.setUser(userRepository.findById(iduUser)
                    .orElseThrow());

            game.setCategory(categoryService.getOrCreateCategory(category));

            return gameRepository.save(game);

        } catch (Exception e){
            throw new RuntimeException("Can't connect with wikipedia");
        }

    }

    public Slice<Game> listUserGames(Integer userId, Pageable pageable) {
        return gameRepository.findByUserId(userId, pageable);
    }

}
