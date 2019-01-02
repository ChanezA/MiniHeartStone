package engine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Represents a MiniHeartStone minion
 * extends the AbstractCard abstract class
 */
@Entity
public class Minion extends AbstractCard {

    @Column(name="attack")
    protected int attack;
    @Column(name="life")
	protected int life;
    @Column(name="hasTaunt")
    protected boolean hasTaunt;
    @Column(name="hasLifeSteal")
    protected boolean hasLifeSteal;
    @Column(name="hasCharge")
    protected boolean hasCharge;

    @Transient
	protected boolean readyToAttack;

    protected Minion() { }

	public Minion(String name, String description, int manaCost, int attack, int life,
			boolean hasTaunt, boolean hasLifeSteal, boolean hasCharge, String pictureURL) {
		
		super(name, description, manaCost, pictureURL);
		this.attack = attack;
		this.life = life;
		this.hasTaunt = hasTaunt;
		this.hasLifeSteal = hasLifeSteal;
		this.hasCharge = hasCharge;

		this.readyToAttack = hasCharge;

	}
	
	public Minion cloneMinion(){
		return new Minion(this.name, this.description, this.manaCost, this.attack, this.life,
				this.hasTaunt, this.hasLifeSteal, this.hasCharge, this.pictureURL);

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
		this.life = life;
	}
	
	public boolean isHasCharge() {
		return this.hasCharge;
	}
	
	public void setHasCharge(boolean bl) {
		this.hasCharge = bl;
	}
	
	public boolean isHasPrococation() {
		return this.hasTaunt;
	}
	
	public void setHasProvocation(boolean bl) {
		this.hasTaunt = bl;
	}
	
	public boolean isHasVolDeVie() {
		return this.hasLifeSteal;
	}
	
	public void setHasVolDeVie(boolean bl) {
		this.hasLifeSteal = bl;
	}
	
	//getter .
	public boolean isReadyToAttack(){
		return this.readyToAttack;
	}
	
	// setter life.
	public void setReadyToAttack(boolean readyToAttack){
		this.readyToAttack = readyToAttack;
	}

	public Minion cloneCard() {
		return new Minion(this.name,this.description,this.manaCost,this.attack,this.life,
				this.hasTaunt,this.hasLifeSteal,this.hasCharge,this.pictureURL);
	}
	
	public String toString() {
		return "life : "+this.life+"\n"+
				"attack : "+this.attack+"\n"+
				"readyToAttack :"+this.readyToAttack+"\n"+
				"MinionUUID : "+this.cardID+"\n"+
				"hasProvocation : "+this.hasTaunt+"\n"+
				"hasLifeSteal : "+this.hasLifeSteal+"\n"+
				"hasCharge : "+this.hasCharge+"\n"+"\n"+

				"manaCost : "+this.manaCost+"\n"+
				"description : "+this.description+"\n"+
				"pictureURL : "+pictureURL+"\n"+
				"name : "+this.name+"\n"+
				"cardID : "+this.cardID;
	}

}
