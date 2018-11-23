package com.miniheartstone.engine;

import java.util.List;
import java.util.ArrayList;

public class Player {
	protected String name;
	protected Hero hero;
	protected int level;
	protected List<Card> hand;
	
	Player(String name,  Hero hero, int level) {
		this.name = name;
		this.hero = hero;
		this.level = level;
		
		List<Card> hand = new ArrayList<Card>();
		hand.add(Game.draw());
		hand.add(Game.draw());
		hand.add(Game.draw());
		hand.add(Game.draw());
	}
	
	protected void invock(Card card) {
		
	}
	
	/*
	 * méthode pour attaquer le hero adverse
	*/
	protected void attackHero(Card card) {
		// remplire
	}
	
	/*
	 * méthode pour attaquer un monstre adverse
	 */
	protected void attack(Card card1,Card card2) {
		// remplire
	}

}
