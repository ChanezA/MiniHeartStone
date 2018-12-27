package engine;

import exception.MiniHeartStoneException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

/**
 * Abstract class
 * Represents a MiniHeartStone card
 */
@Entity
public abstract class Card {

	@Id
	@Column(name="name")
	protected String name;
	@Column(name="description")
	protected String description;
	@Column(name="manaCost")
	protected int manaCost;
	@Transient
	protected String pictureURL;

	@Transient
	protected UUID cardID;

	/**
	 * Instanciates the card to the given parameters and with a random UUID
	 * @param name The name of the card
	 * @param description The description of the card
	 * @param manaCost The mana cost of the card
	 * @param pictureURL The picture of the card that will be displayed for the user
	 */
	public Card(String name, String description, int manaCost, String pictureURL) {
		this.name = name;
		this.description = description;
		this.manaCost = manaCost;
		this.pictureURL = pictureURL;
		
		this.cardID = UUID.randomUUID();
	}

	/**
	 * Clones this card, the returned object will have all the same attributes but a different UUID
	 * @return The cloned Card
	 * @throws MiniHeartStoneException If the method is not overridden
	 */
	public Card cloneCard() throws MiniHeartStoneException {
		throw new MiniHeartStoneException("Error - please override this method");
	}

	/**
	 * Returns the name of the card
	 * @return The name of the card
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the card
	 * @param name The new name of the card
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the description of the card
	 * @return The description of the card
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the card
	 * @param description The new description of the card
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the mana cost of the card
	 * @return The mana cost of the card
	 */
	public int getManaCost() {
		return this.manaCost;
	}

	/**
	 * Sets the mana cost of the card
	 * @param manaCost The new mana cost of the card
	 */
	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	/**
	 * Returns the picture URL of the card
	 * @return The picture URL of the card
	 */
	public String getPictureURL() {
		return this.pictureURL;
	}

	/**
	 * Sets the picture URL of the card
	 * @param pictureURL The new picture URL of the card
	 */
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	/**
	 * Returns the card UUID
	 * @return The card UUID
	 */
	public UUID getCardUUID(){
		return this.cardID;
	}
}
