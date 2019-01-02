package engine;
//import java.util.Properties;
public class Magus extends AbstractHero {
	
	static int PT_DEGATS_POWER_MAGNUS=1;
	
	protected Magus() {
		super("Mage", 30, "Oh lala je suis un mage trrres puissant");
	}

	public void power() {
	}
	
	public String toString() {
		return "I'am Magus, name: " + this.getHeroName() + " mana: "+this.getMana() +" health: "+this.getHealth();
	}
}


