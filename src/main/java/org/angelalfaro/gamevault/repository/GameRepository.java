package org.angelalfaro.gamevault.repository;

import org.angelalfaro.gamevault.entity.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Slice<Game> findByTitleGameContainingContainingIgnoreCase(String title, Pageable pageable);

    // To find the lis of games from a user
    Slice<Game> findByUserId(Integer idUser);

}
