package com.miniheartstone.engine;

import java.util.List;
import java.util.ArrayList;

public class Player {
	protected String name;
	protected Hero hero;
	protected int level;
	protected List<Card> hand;
	protected Game game;
	
	Player(String name,  Hero hero, int level, Game game) {
		this.name = name;
		this.hero = hero;
		this.level = level;
		this.game = game;
		
		List<Card> hand = new ArrayList<Card>();
		hand.add(Game.draw(hero.getName()));
		hand.add(Game.draw(hero.getName()));
		hand.add(Game.draw(hero.getName()));
		hand.add(Game.draw(hero.getName()));
	}
	
	protected void invock(Card card) {
		hand.remove(card);
		card.setIsInvock(true);
		if();
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
