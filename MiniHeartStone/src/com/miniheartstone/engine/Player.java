package com.miniheartstone.engine;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class Player {
	
	protected String pseudo;
	protected Hero hero;
	protected int level;
	protected UUID playerID;
	
	public Player(String pseudo,  Hero hero, int level) {
		this.pseudo = pseudo;
		this.hero = hero;
		this.level = level;
		UUID playerID = UUID.randomUUID();
	}
	
	public Hero getHero() {
		return this.hero;
	}

	public String getPSeudo() {
		return this.pseudo;
	}

	public UUID getPlayerID() {
		return this.playerID;
	}
	
	public void invock(UUID uuid) {
		hand.remove(card);
		card.setIsInvock(true);

		List<Card> cardOnBoard = game.getBoard(this);
		cardOnBoard.add(card);
	}
	
	/*
	 * méthode pour attaquer le hero adverse.
	*/
	public void attack(Minion minion) {
		game.getOpponentPlayer(this).hero.setHealth(
				game.getOpponentPlayer(this).hero.getHealth()-minion.getAttack()
		);
	}
	
	/*
	 * méthode pour attaquer un monstre adverse.
	 */
	public void attack(Minion minion1,Minion minion2) {
		minion1.setLife(
				minion1.getLife()-minion2.getAttack()
		);
		
		minion2.setLife(
				minion2.getLife()-minion1.getAttack()
		);
	}

	public void removeCardHand(Card ourcard) {
		this.hand.remove(ourcard);
		
	}

}
