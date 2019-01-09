package engine;

/**
 * <b>Classe abstraite représentant les différentes cartes de notre jeu.</b>
 *
 * @authors Amri Chanez, Baron Alan, Pineau Alexis, Quetel Louis.
 * @version 1.0.
 */

import exception.MiniHeartStoneException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
public abstract class AbstractCard {

	@Id
	@Column(name = "name")
	protected String name;
	@Column(name = "description")
	private String description;
	@Column(name = "manaCost")
	private int manaCost;
	@Column(name = "heroName")
	private String heroName;
	@Column(name = "pictureURL")
	private String pictureURL;

	@Transient
	protected UUID cardID;

	/**
	 * Empty conctructor.
	 */
	protected AbstractCard() { }

	/**
	 * Instantiates the card to the given parameters and with a random UUID
	 * @param name The name of the card
	 * @param description The description of the card
	 * @param manaCost The mana cost of the card
	 * @param heroName The hero class to which the card belongs, null if this card is available for all heroes
	 * @param pictureURL The picture of the card that will be displayed for the user
	 */
	public AbstractCard(String name, String description, int manaCost, String heroName, String pictureURL) {
		this.name = name;
		this.description = description;
		this.manaCost = manaCost;
		this.heroName = heroName;
		this.pictureURL = pictureURL;
		
		this.cardID = UUID.randomUUID();
	}

	/**
	 * Clones this card, the returned object will have all the same attributes but a different UUID
	 * @return The cloned AbstractCard
	 * @throws MiniHeartStoneException If the method is not overridden
	 */
	public AbstractCard cloneCard() throws MiniHeartStoneException {
		throw new MiniHeartStoneException(
                "Error - please override this method");
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
	 * @param cname The new name of the card
	 */
	public void setName(final String cname) {
		this.name = cname;
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
	 * @param desc The new description of the card
	 */
	public void setDescription(final String desc) {
		this.description = desc;
	}

	/**
	 * Returns the hero of the card
	 * @return The hero of the card
	 */
	public String getHeroName() {
		return this.heroName;
	}

	/**
	 * Sets the hero of the card
	 * @param hname The new hero of the card
	 */
	public void setHeroName(final String hname) {
		this.heroName = hname;
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
	 * @param mana The new mana cost of the card
	 */
	public void setManaCost(final int mana) {
		this.manaCost = mana;
	}

	/**
	 * Returns the picture URL of the card.
	 * @return The picture URL of the card
	 */
	public String getPictureURL() {
		return this.pictureURL;
	}

	/**
	 * Sets the picture URL of the card.
	 * @param picture The new picture URL of the card
	 */
	public void setPictureURL(final String picture) {
		this.pictureURL = picture;
	}

	/**
	 * Returns the card UUID.
	 * @return this.cardID
	 */
	public UUID getCardUUID() {
		return this.cardID;
	}
}
