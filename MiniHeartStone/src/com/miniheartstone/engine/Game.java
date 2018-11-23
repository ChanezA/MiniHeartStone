package com.miniheartstone.engine;

import java.util.ArrayList;

public class Game {

    private Player currentPlayer;

    private Player player1;
    private Player player2;

    private ArrayList<Card> boardP1;
    private ArrayList<Card> boardP2;

    private int round;

    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }



}
