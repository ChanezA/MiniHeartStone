package com.miniheartstone.engine;
//import java.util.Properties;
public class Magus extends Hero {
	static int PT_DEGATS_POWER_MAGNUS=1;
	public Magus(String name, int health, int mana) {
		super(name,health,mana);
	}
	void power(Game palteau) {
		System.out.println("Boule de feu infligeant un point de degats à un adversaire serviteur ou hero");
		//plateau.attaque(""serviteur ou hero,PT_DEGATS_POWER_MAGNUS");
	}
	
	public String toString() {
		return "I'am Magus, name: " + this.name + " mana: "+this.mana +" health: "+this.health;
	}
}


