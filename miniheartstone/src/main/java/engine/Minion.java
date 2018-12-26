package engine;

import java.util.UUID;
/*
 * Louis le bg
 *  Nice end
 */
public class Minion extends Card {
	
	protected int life;
	protected int attack;
	
	protected boolean readyToAttack;
	protected UUID MinionUUID;
	
	protected boolean hasProvocation;
	protected boolean hasVolDeVie;
	protected boolean hasCharge;
	
	Minion(int life, int attack,int manaCost, String name, String description, String pictureURL,
			boolean hasProvocation, boolean hasVolDeVie, boolean hasCharge){
		
		super(manaCost, name, pictureURL, description);
		this.life = life;
		this.attack = attack;
		
		this.readyToAttack = false;
		this.MinionUUID = UUID.randomUUID();
		
		this.hasProvocation = hasProvocation;
		this.hasVolDeVie = hasVolDeVie;
		this.hasCharge = hasCharge;
	}
	
	public Minion cloneMinion(){
		Minion theClone = new Minion(this.getLife(), this.getAttack(),this.getManaCost(), this.getName(), this.getDescription(), this.getPictureURL(),
				this.getHasPrococation(), this.getHasVolDeVie(), this.getHasCharge());
		return theClone;
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
	
	public boolean getHasCharge() {
		return this.hasCharge;
	}
	
	public void setHasCharge(boolean bl) {
		this.hasCharge = bl;
	}
	
	public boolean getHasPrococation() {
		return this.hasProvocation;
	}
	
	public void setHasProvocation(boolean bl) {
		this.hasProvocation = bl;
	}
	
	public boolean getHasVolDeVie() {
		return this.hasVolDeVie;
	}
	
	public void setHasVolDeVie(boolean bl) {
		this.hasVolDeVie = bl;
	}
	
	//getter .
	public boolean getReadyToAttack(){
		return this.readyToAttack;
	}
	
	// setter life.
	public void setReadyToAttack(boolean readyToAttack){
		this.readyToAttack = readyToAttack;
	}
	
	public String toString() {
		return "life : "+this.life+"\n"+
				"attack : "+this.attack+"\n"+
				"readyToAttack :"+this.readyToAttack+"\n"+
				"MinionUUID : "+this.MinionUUID+"\n"+
				"hasProvocation : "+this.hasProvocation+"\n"+
				"hasLifeSteal : "+this.hasVolDeVie+"\n"+
				"hasCharge : "+this.hasCharge+"\n"+"\n"+

				"manaCost : "+this.manaCost+"\n"+
				"description : "+this.description+"\n"+
				"pictureURL : "+pictureURL+"\n"+
				"name : "+this.name+"\n"+
				"cardID : "+this.cardID;
	}
	
	public static void main(String[] args) {
		Card card = new Minion(6, 6,6, "bg", "je suis tr�s beau", null,true, true, true);
		System.out.println(card.toString());
	}
}
