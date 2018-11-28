package com.miniheartstone.engine;
//import java.util.Properties;
public class Paladin extends Hero {
	
	public Paladin (String name, int health, int mana) {
		super(name,health,mana);
	}
	void power(Game palteau) {
		System.out.println("Renfort, invoquant un serviteur recrue de la Main d'argent");
		//Serviteur serviteur=new Recrue_de_la_main_d_argent();
		//Game.add(serviteur);
	}
	public String toString() {
		return "I'am Paldin" + this.name + "mana:"+this.mana +"health"+this.health;
	}
}
