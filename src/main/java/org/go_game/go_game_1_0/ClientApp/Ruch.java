package org.go_game.go_game_1_0.ClientApp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ruchy")
public class Ruch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "move")
    private String move;

    @Column(name = "kolor")
    private String kolor;

    @Column(name = "czas_ruchu")
    private LocalDateTime czasRuchu;

    // Konstruktor, gettery i settery

    public Ruch() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public String getKolor() {
        return kolor;
    }

    public void setKolor(String kolor) {
        this.kolor = kolor;
    }

    public LocalDateTime getCzasRuchu() {
        return czasRuchu;
    }

    public void setCzasRuchu(LocalDateTime czasRuchu) {
        this.czasRuchu = czasRuchu;
    }

}
