package engine;
/*
 * Louis le bg
 *  Nice end
 */
import java.util.UUID;

abstract class Card {
	
	protected int manaCost;
	protected String description;
	protected String pictureURL;
	
	protected String name;
	protected UUID cardID;
	
	public Card(int manaCost, String name, String pictureURL, String description) {
		this.manaCost = manaCost;
		this.name = name;
		this.pictureURL = pictureURL;
		this.description = description;
		
		this.cardID = UUID.randomUUID();
	}
	
	public Card cloneCard() {
		if (this instanceof Minion ) {
			return (Card)(((Minion)this).cloneMinion());
		}
		else {
			return (Card)(((Spell)this).cloneSpell());
		}
	}
	
	public UUID getCardUUID(){
		return this.cardID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	public int getManaCost() {
		return this.manaCost;
	}
	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPictureURL() {
		return this.pictureURL;
	}
}
