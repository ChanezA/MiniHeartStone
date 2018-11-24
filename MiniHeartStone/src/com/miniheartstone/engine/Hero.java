package com.miniheartstone.engine;
import java.util.Properties;
abstract class Hero {
		static int MANA_MAX= 10;
		protected String name;
		protected int health;
		protected int mana;
		protected int armor =0;
		//liste tte les carte que le heros peut avoir a recuperer dans la base de données
		protected int manaMax =MANA_MAX;
		
		protected Hero(String name, int health, int mana) {
			this.name= name;
			this.health=health;
			this.mana=mana;
			this.armor =0;
		}
		abstract void power(Game plateau);
		public String getName() {
			return this.name;
		}
		public int getHealth() {
			return this.health;
		}
		public int getMana() {
			return this.mana;
		}
		public int getArmor() {
			return this.armor;
		}
		abstract String display();
			
		
}
