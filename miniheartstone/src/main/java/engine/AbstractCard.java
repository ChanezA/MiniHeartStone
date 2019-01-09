package engine;

/**
 * <b>Classe abstraite représentant les différentes cartes de notre jeu.</b>
 *
 * @authors Amri Chanez, Baron Alan, Pineau Alexis, Questel Louis.
 * @version 1.0
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
	protected String description;
	@Column(name = "manaCost")
	protected int manaCost;
	@Column(name = "heroName")
	protected String heroName;
	@Column(name = "pictureURL")
	protected String pictureURL;

	@Transient
	protected UUID cardID;

	/**
	 * Empty conctructor.
	 */
	protected AbstractCard() { }

	/**
	 * Instantiates the card to the given parameters and with a random UUID.
	 * @param cName The name of the card
	 * @param desc The description of the card
	 * @param mana The mana cost of the card
	 * @param hName The hero class to which the card belongs,
     *              null if this card is available for all heroes
	 * @param picture The picture of the card that will be displayed
     *                for the user
	 */
	public AbstractCard(final String cName, final String desc,
                        final int mana, final String hName,
                        final String picture) {
		this.name = cName;
		this.description = desc;
		this.manaCost = mana;
		this.heroName = hName;
		this.pictureURL = picture;
		this.cardID = UUID.randomUUID();
	}

	/**
	 * Clones this card, the returned object will have all the same
     * attributes but a different UUID.
	 * @return The cloned AbstractCard
	 * @throws MiniHeartStoneException If the method is not overridden
	 */
	public AbstractCard cloneCard() throws MiniHeartStoneException {
		throw new MiniHeartStoneException(
                "Error - please override this method");
	}

	/**
	 * Returns the name of the card.
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the card.
	 * @param cname The new name of the card
	 */
	public void setName(final String cname) {
		this.name = cname;
	}

	/**
	 * Returns the description of the card.
	 * @return this.description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the card.
	 * @param desc The new description of the card
	 */
	public void setDescription(final String desc) {
		this.description = desc;
	}

	/**
	 * Returns the hero of the card.
	 * @return this.heroName
	 */
	public String getHeroName() {
		return this.heroName;
	}

	/**
	 * Sets the hero of the card.
	 * @param hname The new hero of the card
	 */
	public void setHeroName(final String hname) {
		this.heroName = hname;
	}

	/**
	 * Returns the mana cost of the card.
	 * @return this.manaCost
	 */
	public int getManaCost() {
		return this.manaCost;
	}

	/**
	 * Sets the mana cost of the card.
	 * @param mana The new mana cost of the card
	 */
	public void setManaCost(final int mana) {
		this.manaCost = mana;
	}

	/**
	 * Returns the picture URL of the card.
	 * @return this.pictureURL
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
