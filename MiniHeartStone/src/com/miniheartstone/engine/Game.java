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

        if (this.round < Hero.MANA_MAX) hero.setMana(this.round);
        else hero.setMana(Hero.MANA_MAX);

        Game.draw(hero);
    }

    /**
     * Helper Method (initRound)
     */
    protected static Card draw(Hero hero) {
        // TODO implémenter cette méthode.
    	Minion card = new Minion(69, 69,1, "mdr j'ai le num�ro de la m�re de Alan", "0659453256",null, "lol/vas/y/appel/fait/tooi/plaise");
    	return card;
    }
    
    // permet au joueur player de r�cupperer son board
    public List<Card> getBoard(Player player){
    	if (player == this.player1 ) {
    		return this.boardP1;
    	}
    	else {
    		return this.boardP2;
    	}
    }
    
    // permet au joueur player de r�cupperer le voard de son adversaire
    public List<Card> getOpponentBoard(Player player){
    	if (player == this.player1 ) {
    		return this.boardP2;
    	}
    	else {
    		return this.boardP1;
    	}
    }
    
    public Player getOpponentPlayer(Player player){
    	if (player == this.player1 ) {
    		return this.player1;
    	}
    	else {
    		return this.player2;
    	}
    }
    

}
