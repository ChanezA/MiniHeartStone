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
        this.round = 0;
    }

    // Getters
    Player getCurrentPlayer() { return this.currentPlayer; }
    Player getPlayer1() { return this.player1; }
    Player getPlayer2() { return this.player2; }
    List<Card> getBoardP1() { return this.boardP1; }
    List<Card> getBoardP2() { return this.boardP2; }
    int getRound() { return this.round; }

    /**
     * Does all the action of a round beginning
     */
    protected void initRound() {
        Player currP = this.currentPlayer;
        Hero hero = currP.getHero();

        if (this.round < hero.MANA_MAX) hero.setMana(this.round);
        else hero.setMana(hero.MANA_MAX);

        this.draw();
    }

    /**
     * Helper Method (initRound)
     */
    protected static Card draw(String hero) {
        // TODO implémenter cette méthode
    	Card card = new Minion(1, "twerk", "http:photoDossier/&normeTeuf", "Cette carte est dingue", null);
    	return card;
    }

}
