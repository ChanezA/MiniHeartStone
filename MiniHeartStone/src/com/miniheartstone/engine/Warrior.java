package com.miniheartstone.engine;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
import java.util.Properties;
//import java.io.IOException;

public class Warrior extends Hero {
	Properties prop =new Properties();
	
	
	
/*	private void setprop(String key,String value) {
		OutputStream os = new FileOutputStream("Constantes.properties");
		prop.setProperty("Height", "200");
		prop.store(os,null);
	}
	*/
	
	static int PT_ARMOR_AUGMENTATION=2;
	public Warrior(String name, int health, int mana,int armure) {
		super(name,health,mana);
		this.armor=armure;
	}
	void power(Game palteau) {
		System.out.println("lui confère deux point d'armure");
		this.armor=this.armor+PT_ARMOR_AUGMENTATION;
	}
	public String toString() {
		return "I'am Warrior, name: " + this.name + " mana:"+this.mana +" health"+this.health +" armor"+ this .armor;
	}
}
