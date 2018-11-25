package com.miniheartstone.engine;

public class Minion extends Card {
	
	protected int life;
	protected int attack;
	protected boolean readyToAttack;
	
	Minion(int life, int attack,int manaCost, String name, String description,Effect effect, String pictureURL){
		super(manaCost, name, pictureURL, description, effect);
		this.life = life;
		this.attack = attack;
		this.readyToAttack = false;
	}
	
	//getter attack
	public int getAttacck(){
		return this.attack;
	}
	
	// setter attack
	public void setAttack(int att){
		this.attack = att;
	}
	
	//getter life
	public int getLife(){
		return this.life;
	}
	
	// setter life
	public void setLife(int life){
		this.attack = life;
	}
	
	//getter 
	public boolean getReadyToAttack(){
		return this.readyToAttack;
	}
	
	// setter life
	public void setReadyToAttack(boolean readyToAttack){
		this.readyToAttack = readyToAttack;
	}
}
