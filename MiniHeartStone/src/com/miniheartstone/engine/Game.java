package com.miniheartstone.engine;

import java.util.ArrayList;
import java.util.UUID;


public class Game {

    // Private attributes
    private Player currentPlayer;
    private Player notCurrentPlayer;
    private Player player1;
    private Player player2;
    
    private UUID gameID;

    public static int MANA_MAX = 10;

    // Constructor
    public Game(Player player1, Player player2) {
        this.player1  = player1;
        this.player2 = player2;
        
        UUID gameID = UUID.randomUUID();
        
        this.initGame();
    }

    // Getters
    public Player getCurrentPlayer() {
    	return this.currentPlayer;
    }
    public Player getNotCurrentPlayer() {
        return this.notCurrentPlayer;
    }
    public Player getPlayer1() { return this.player1; }
    public Player getPlayer2() { return this.player2; }
    
    public UUID getGameID () {return this.gameID;}
    
    //Setters
    public void setCurrentPlayer(Player player) {
    	this.currentPlayer = player;
    }
    public void setNotCurrentPlayer(Player player) {
    	this.notCurrentPlayer = player;
    }

    /**
     * Initializes the game
     */
    private void initGame() {
        this.currentPlayer = player1;
        int i;
        for (i = 0; i < 4; i++) {
            player1.getHero().draw();
            player2.getHero().draw();
        }
        this.getCurrentPlayer().getHero().setMana(1);
        this.getNotCurrentPlayer().getHero().setMana(0);
    }

    /**
     * passage de tour
     */
    private void passTurn(UUID playerID) {
    	if(this.CurrentPlayerOrNot(playerID)) {
	    	// echange le joueur courant avec le non courant
	        Player tmp = this.getCurrentPlayer();
	        this.setCurrentPlayer(this.getNotCurrentPlayer());
	        this.setNotCurrentPlayer(tmp);
	        
	        // incrémentation du mana pour le nouveau joueur courant si besoin
	        if(this.getCurrentPlayer().getHero().getMana() < MANA_MAX) {
	        	this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()+1);
	        }
	
	        this.getCurrentPlayer().getHero().draw();
    	}
    }
    
    // retourne vrai si le joueur passé en param est courant ou faux pour tout le reste
    public boolean CurrentPlayerOrNot(UUID playerUUID) {
    	boolean crt = false;
    	if(this.getCurrentPlayer().getPlayerID() == playerUUID) {
    		crt = true;
    	}
    	return crt;
    }

    /**
     * Returns the board of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public ArrayList<Card> getMyBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer1().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer2().getHero().getBoard();
    	}
    }

    /**
     * Returns the board the opponent of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public ArrayList<Card> getOpponentBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer2().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer1().getHero().getBoard();
    	}
    }
    
    public void invock(UUID playerUUID, UUID cardID) {
    	// si tu es bien le joueur courant et que la carte est bien dans ta main et que tu as bien le mana necessaire
    	if(this.CurrentPlayerOrNot(playerUUID) && this.getCurrentPlayer().getHero().isOnMyHand(cardID) && this.getCurrentPlayer().getHero().getMana() >= this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getManaCost()) {
    		
    		// si cette carte est un minion
    		if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Minion) {
    			this.getCurrentPlayer().getHero().invock(cardID);
    		}
    		// si c'est un spell
    		else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Spell) {
    			// si ce n'est pas un spell qui affecte l'enemi ni un spell qui necessite un siblage
    			if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Image miroir"
    					|| this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Maîtrise du blocage") {
    				this.getCurrentPlayer().getHero().invock(cardID);
    			}
    			
    			// si c'est un spell c'est tourbilol
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Tourbillon") {
    				// on retire 1 pv a toutes les invocations des bord des deux joueurs
    				for(int i=0; i<this.getCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getCurrentPlayer().getHero().getBoard().get(i));
    					this.getCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en conséquence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
    			}
    			
    			// si c'est le spell consécration
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Consécration") {
    				// retire 2 pdv a tous les minions adverse
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 2);
    				}
    				// degats sur le joueur adverse
    				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(2);
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en conséquence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
    			}
    			
    			// si le spell c'est explosion des arcanes
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Explosion des arcanes") {
    				// retire 1 pdv a tous les minions adverse
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en conséquence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
    			}
    			// si le spell c'est Métamorphose
    			
    			// si le spell c'est Bénédiction de puissance
    		}
    	}
    }
}

/*
|| this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Consécration"
|| this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Explosion des arcanes"
*/
