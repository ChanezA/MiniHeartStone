package com.miniheartstone.engine;

import java.util.List;
import java.util.ArrayList;

public class Player {
	protected String name;
	protected Hero hero;
	protected int level;
	protected List<Card> hand;
	protected Game game;
	
	public Player(String name,  Hero hero, int level) {
		this.name = name;
		this.hero = hero;
		this.level = level;
		
		List<Card> hand = new ArrayList<Card>();
	}
	
	public Hero getHero() {
		return this.hero;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	public void invock(Card card) {
		hand.remove(card);
		card.setIsInvock(true);

		List<Card> cardOnBoard = game.getBoard(this);
		cardOnBoard.add(card);
	}
	
	/*
	 * méthode pour attaquer le hero adverse.
	*/
	public void attackHero(Minion minion) {
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

}
