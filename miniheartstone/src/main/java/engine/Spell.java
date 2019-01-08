package engine;
/*
 * Louis le bg
 *  Nice end
 */
import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Spell extends AbstractCard {

	/**
	 * Empty constructor
	 */
	protected Spell() {

	}

	/**
	 * Instantiates the spell to the given parameters and with a random UUID
	 * @param manaCost The mana cost of the spell
	 * @param name The name of the spell
	 * @param description The description of the spell
	 * @param heroName The hero class to which the card belongs, null if this card is available for all heroes
	 * @param pictureURL The picture of the spell that will be displayed for the user
	 */
	public Spell(int manaCost, String name, String description, String heroName, String pictureURL){
		super(name, description, manaCost, heroName, pictureURL);
		
		this.cardID = UUID.randomUUID();
	}

	@Override
	public Spell cloneCard() {
		return new Spell(this.manaCost,this.name,this.description,this.heroName,this.pictureURL);
	}

	@Override
	public String toString() {
		return	"MinionUUID : "+this.cardID+"\n"+"\n"+
				
				"manaCost : "+this.manaCost+"\n"+
				"description : "+this.description+"\n"+
				"pictureURL : "+pictureURL+"\n"+
				"name : "+this.name+"\n"+
				"cardID : "+this.cardID+
				"heroName :"+this.heroName;
	}

}
