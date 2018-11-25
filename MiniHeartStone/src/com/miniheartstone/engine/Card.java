package com.miniheartstone.engine;

abstract class Card {
	 
	protected int manaCost;
	//Every effect the card has
	protected Effect effect;
	protected String pictureURL;
	protected String name;
	protected String description;
	protected boolean isInvock;
	
	protected Card(int manaCost, String name, String pictureURL, String description, Effect effect) {
		this.manaCost = manaCost;
		this.name = name;
		this.pictureURL = pictureURL;
		this.description = description;
		this.effect = effect;
		this.isInvock = false;
	}
	
	Effect getEffect() {
		return effect;
	}
	
	int getManaCost() {
		return manaCost;
	}
	
	String getName() {
		return name;
	}
	
	String getDescription() {
		return description;
	}
	
	boolean getIsInvock() {
		return isInvock;
	}
	
	void setIsInvock(boolean bool) {
		this.isInvock = bool;
	}
}
