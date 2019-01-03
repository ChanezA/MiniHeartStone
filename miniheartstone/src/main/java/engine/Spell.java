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
	 * @param pictureURL The picture of the spell that will be displayed for the user
	 */
	public Spell(int manaCost, String name, String description, String pictureURL){
		super(name, description, manaCost, pictureURL);
		
		this.cardID = UUID.randomUUID();
	}
	
	public Spell cloneSpell(){
		return new Spell(this.getManaCost(), this.getName(), this.getDescription(), this.getPictureURL());
	}

	public Spell cloneCard() {
		return new Spell(this.manaCost,this.name,this.description,this.pictureURL);
	}
	
	public String toString() {
		return	"MinionUUID : "+this.cardID+"\n"+"\n"+
				
				"manaCost : "+this.manaCost+"\n"+
				"description : "+this.description+"\n"+
				"pictureURL : "+pictureURL+"\n"+
				"name : "+this.name+"\n"+
				"cardID : "+this.cardID;
	}

}
