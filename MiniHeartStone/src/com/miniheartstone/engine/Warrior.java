package com.miniheartstone.engine;

public class Warrior extends Hero {
	
	static int PT_ARMOR_AUGMENTATION=2;
	
	public Warrior() {
		super("Guerrier", 30, " mdr je suis un guerrier tres fort");
	}
	
	
	void power() {
		this.setArmor(this.getArmor()+PT_ARMOR_AUGMENTATION);
	}
	
	public String toString() {
		return "I'am Warrior, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
}
