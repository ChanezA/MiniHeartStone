package engine;
/*
 * Louis le bg
 *  Nice end
 */
import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Spell extends Card {
	
	Spell(int manaCost, String name, String description, String pictureURL){
		
		super(name, description, manaCost, pictureURL);
		
		this.cardID = UUID.randomUUID();
	}
	
	public Spell cloneSpell(){
		Spell theClone = new Spell(this.getManaCost(), this.getName(), this.getDescription(), this.getPictureURL());
		return theClone;
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
