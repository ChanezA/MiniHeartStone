package com.miniheartstone.engine;

import java.util.ArrayList;
import java.util.List;

public class Game {

    // Private attributes
    private Player currentPlayer;

    private Player player1;
    private Player player2;

    private List<Card> boardP1;
    private List<Card> boardP2;

    private int round;

    // Constructor
    Game(Player player1, Player player2) {
        this.player1  = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.boardP1 = new ArrayList<Card>();
        this.boardP2 = new ArrayList<Card>();
    }

    // Getters
    Player getCurrentPlayer() { return this.currentPlayer; }

    Player getPlayer1() { return this.player1; }

    Player getPlayer2() { return this.player2; }

    List<Card> getBoardP1() { return this.boardP1; }

    List<Card> getBoardP2() { return this.boardP2; }

}
