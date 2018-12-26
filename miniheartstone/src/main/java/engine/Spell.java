package engine;
/*
 * Louis le bg
 *  Nice end
 */
import java.util.UUID;

public class Spell extends Card {
	
	protected UUID MinionUUID;
	
	Spell(int manaCost, String name, String description, String pictureURL){
		
		super(manaCost, name, pictureURL, description);
		
		this.MinionUUID = UUID.randomUUID();
	}
	
	public Spell cloneSpell(){
		Spell theClone = new Spell(this.getManaCost(), this.getName(), this.getDescription(), this.getPictureURL());
		return theClone;
	}
	
	public String toString() {
		return	"MinionUUID : "+this.MinionUUID+"\n"+"\n"+
				
				"manaCost : "+this.manaCost+"\n"+
				"description : "+this.description+"\n"+
				"pictureURL : "+pictureURL+"\n"+
				"name : "+this.name+"\n"+
				"cardID : "+this.cardID;
	}
	
	public static void main(String[] args) {
		Card maBite = new Spell(5, "Enorme Sexe Gold�", "tmtc c ma bite lol c==3", null);
		System.out.println(maBite.toString());
	}
}
