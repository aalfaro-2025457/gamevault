package org.angelalfaro.gamevault.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String titleGame;

    @Column(name = "wikiSlug", unique = true)
    private String wikiSlug; // Example: "The_Legend_of_Zelda"

    @Column(name = "image_url", length = 1000)
    private String imageUrlGame; // the url of the image

    @Column
    private int statusGame; // 0 = disable , 1 = enabled , 2 = playing , 3 = finished

    @JsonIgnoreProperties("user")
    @ManyToOne
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_id_user"))
    private User user;

    @JsonIgnoreProperties("category")
    @ManyToOne
    @JoinColumn(name = "idCategory", foreignKey = @ForeignKey(name = "FK_id_category"))
    private Category category;

}
