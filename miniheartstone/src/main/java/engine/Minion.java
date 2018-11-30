package engine;

import javax.persistence.Entity;

@Entity
public class Minion extends Card {
	
	protected int life;
	protected int attack;
	protected boolean readyToAttack;

	protected Minion() {}

	public Minion(String name, String description, int life, int attack,int manaCost, Effect effect, String pictureURL){
		super(name, description, manaCost, pictureURL, effect);
		this.life = life;
		this.attack = attack;
		this.readyToAttack = false;
	}
	
	//getter attack.
	public int getAttack(){
		return this.attack;
	}
	
	// setter attack.
	public void setAttack(int att){
		this.attack = att;
	}
	
	//getter life.
	public int getLife(){
		return this.life;
	}
	
	// setter life.
	public void setLife(int life){
		this.attack = life;
	}
	
	//getter .
	public boolean getReadyToAttack(){
		return this.readyToAttack;
	}
	
	// setter life.
	public void setReadyToAttack(boolean readyToAttack){
		this.readyToAttack = readyToAttack;
	}

	@Override
	public String toString() {
		return "Bonjour je suis un serviteur";
	}
}
