package org.angelalfaro.gamevault.repository;

import org.angelalfaro.gamevault.entity.Game;
import org.angelalfaro.gamevault.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Slice<Game> findByTitleGameContainingIgnoreCase(String title, Pageable pageable);

    // To find the lis of games from a user
    Slice<Game> findByUser_IdUser(Integer idUser, Pageable pageable);

    Optional<Game> findByWikiSlugAndUser(String wikiSlug, User user);

}
