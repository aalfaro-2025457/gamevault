package org.angelalfaro.gamevault.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(nullable = false, unique = true)
    private String usernameUser;

    @Column(nullable = false)
    private String passwordUser;

    // One to many :One user have many games
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Game> myGames;

}
