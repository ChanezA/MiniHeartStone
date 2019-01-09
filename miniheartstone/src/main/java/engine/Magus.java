package engine;
//import java.util.Properties;
public class Magus extends AbstractHero {
	
	public static final int DEGATS_POWER = 1;
	
	protected Magus() {
		super("Mage", 30, "Oh lala je suis un mage trrres puissant");
	}

	public void power() {
	}
	
	public String toString() {
		return "I'am Magus, name: " + this.getHeroName() + " mana: "+this.getMana() +" health: "+this.getHealth();
	}
}


