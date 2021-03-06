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
    @Column(name="has_taunt")
    protected boolean hasTaunt;
    @Column(name="has_life_steal")
    protected boolean hasLifeSteal;
    @Column(name="has_charge")
    protected boolean hasCharge;

    @Transient
	protected boolean readyToAttack;

	/**
	 * Empty conctructor for Spring
	 */
    protected Minion() {
		super();
		this.readyToAttack = false;
	}

	/**
	 * Instantiates the minion to the given parameters and with a random UUID
	 * @param name The name of the minion
	 * @param description The description of the minion
	 * @param manaCost The mana cost of the minion
	 * @param attack The attack of the minion
	 * @param life The life of the minion
	 * @param hasTaunt Boolean set to true if the minion has the effect taunt, false otherwise
	 * @param hasLifeSteal Boolean set to true if the minion has the effect life steal, false otherwise
	 * @param hasCharge Boolean set to true if the minion has the effect charge, false otherwise
	 * @param heroName The hero class to which the card belongs, null if this card is available for all heroes
	 * @param pictureURL The picture of the minion that will be displayed for the user
	 */
	public Minion(String name, String description, int manaCost, int attack, int life,
			boolean hasTaunt, boolean hasLifeSteal, boolean hasCharge,String heroName, String pictureURL) {
		
		super(name, description, manaCost,heroName, pictureURL);
		this.attack = attack;
		this.life = life;
		this.hasTaunt = hasTaunt;
		this.hasLifeSteal = hasLifeSteal;
		this.hasCharge = hasCharge;

		this.readyToAttack = hasCharge;
	}

	@Override
	public Minion cloneCard() {
		return new Minion(this.name,this.description,this.manaCost,this.attack,this.life,
				this.hasTaunt,this.hasLifeSteal,this.hasCharge,this.heroName,this.pictureURL);
	}

	/**
	 * Returns the attack of the minion
	 * @return The attack of the minion
	 */
	public int getAttack(){
		return this.attack;
	}

	/**
	 * Sets the attack of the minion
	 * @param att The new attack of the minion
	 */
	public void setAttack(int att){
		this.attack = att;
	}

	/**
	 * Returns the life of the minion
	 * @return The life of the minion
	 */
	public int getLife(){
		return this.life;
	}

	/**
	 * Sets the life of the minion
	 * @param life The new life of the minion
	 */
	public void setLife(int life){
		this.life = life;
	}

	/**
	 * Returns true if the minion has the charge effect, false otherwise
	 * @return hasCharge
	 */
	public boolean getHasCharge() {
		return this.hasCharge;
	}

	/**
	 * Sets the attribute hasCharge
	 * @param hasCharge The new value of hasCharge
	 */
	public void setHasCharge(boolean hasCharge) {
		this.hasCharge = hasCharge;
	}

	/**
	 * Returns true if the minion has the Taunt effect, false otherwise
	 * @return hasTaunt
	 */
	public boolean getHasTaunt() {
		return this.hasTaunt;
	}

	/**
	 * Sets the attribute hasTaunt
	 * @param hasTaunt The new value of hasTaunt
	 */
	public void setHasTaunt(boolean hasTaunt) {
		this.hasTaunt = hasTaunt;
	}

	/**
	 * Returns true if the minion has the charge life steal, false otherwise
	 * @return hasLifeSteal
	 */
	public boolean getHasLifeSteal() {
		return this.hasLifeSteal;
	}

	/**
	 * Sets the attribute hasLifeSteal
	 * @param hasLifeSteal The new value of hasLifeSteal
	 */
	public void setHasLifeSteal(boolean hasLifeSteal) {
		this.hasLifeSteal = hasLifeSteal;
	}

	/**
	 * Returns true if the minion is ready to attack, false otherwise
	 * @return readyToAttack
	 */
	public boolean isReadyToAttack(){
		return this.readyToAttack;
	}

	/**
	 * Sets the attribute readyToAttack
	 * @param readyToAttack The new value of readyToAttack
	 */
	public void setReadyToAttack(boolean readyToAttack){
		this.readyToAttack = readyToAttack;
	}

	@Override
	public String toString() {
		return  "== Minion ==\nname:"+this.name+"\n"+
				"description:"+this.description+"\n"+
				"manaCost:"+this.manaCost+"\n"+"attack:"+this.attack+"\n"+
				"life:"+this.life+"\n"+
				"hasTaunt:"+this.hasTaunt+"\n"+
				"hasLifeSteal:"+this.hasLifeSteal+"\n"+
				"hasCharge:"+this.hasCharge+"\n"+"readyToAttack :"+this.readyToAttack+"\n"+
				"heroName :"+this.heroName+"\n"+
				"pictureURL : "+pictureURL+"\n"+"cardUUID : "+this.cardID+"\n";
	}

}
