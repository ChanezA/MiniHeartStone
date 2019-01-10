package engine;

public class Warrior extends AbstractHero {
	
	public static final int PT_ARMOR = 2;
	
	public Warrior() {
		super("Guerrier", 30, " mdr je suis un guerrier tres fort");
	}

	public void power() {
		this.setArmor(this.getArmor()+ PT_ARMOR);
	}

	public String toString() {
		return "I'am Warrior, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
}
