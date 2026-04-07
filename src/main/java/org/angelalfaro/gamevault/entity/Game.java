package org.angelalfaro.gamevault.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGame;

    @Column(nullable = false)
    private String title;

    @Column(name = "wikiSlug", unique = true)
    private String wikiSlug; // Example: "The_Legend_of_Zelda"

    @Column(name = "image_url", length = 1000)
    private String imageUrl; // the url of the image

    @Column
    private int status; // 0 = disable , 1 = enabled , 2 = playing , 3 = finished

}
