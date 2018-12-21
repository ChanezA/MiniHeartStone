package engine;

//import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import java.util.UUID;

@Entity
public abstract class Card {

	@Id
	@Column(name="name")
	protected String name;
	@Column(name="description")
	protected String description;
	@Column(name="manaCost")
	protected int manaCost;
	protected String pictureURL;
	@Transient
	protected boolean isInvock;
	@Transient
	protected boolean hasAttacked;
	@Transient
	protected Effect effect;
	@Transient
	protected UUID cardID;

	protected Card() {}

	public Card(String name, String description, int manaCost, String pictureURL, Effect effect) {
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
	
	void setHasAttacked(boolean hasattacked) {
		this.hasAttacked=hasattacked;
	}
	
	boolean hasAttacked () {
		return this.hasAttacked;
		
	}

	int getAttack() {
		return 0;
	}

	UUID getCardID() {
		return this.cardID;
	}

	@Override
	public String toString() {
		return "SVP implémentez cette méthode dans votre classe";
	}
}
