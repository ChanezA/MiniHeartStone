package engine;
//test
public abstract class Hero_Doublure extends Hero {
	
	public Hero_Doublure(String HeroName, int health, String description) {
		super(HeroName,health,description);
	}
	
	public Card draw(String cardName) {
		if(cardName == "Sanglier brocheroc") {
			Card min = new Minion(1,1,1,"Sanglier brocheroc","description","picture",false,false,false);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Soldat du comté-de-l'or") {
			Card min = new Minion(2,1,1,"Soldat du comté-de-l'or","description","picture",true,false,false);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Chevaucheur de loup") {
			Card min = new Minion(1,3,3,"Soldat du comté-de-l'or","description","picture",false,false,true);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Chef de raid") {
			Card min = new Minion(2,2,3,"Chef de raid","description","+1 d'att a tous les minions alliés",false,false,false);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Yéti noroit") {
			Card min = new Minion(4,5,4,"Yéti noroit","description","img",false,false,false);
			hand.add(min);
			return min;
		}
		
		// Spell(int manaCost, String name, String description, String pictureURL)
		else if(cardName == "Image miroir") {
			Card min = new Spell(1,"Image miroir","description","img");
			hand.add(min);
			return min;
		}
		
		// Spell(int manaCost, String name, String description, String pictureURL)
		else if(cardName == "Explosion des arcanes") {
			Card min = new Spell(2,"Explosion des arcanes","description","img");
			hand.add(min);
			return min;
		}
		
		// Spell(int manaCost, String name, String description, String pictureURL)
		else if(cardName == "Métamorphose") {
			Card min = new Spell(4,"Métamorphose","description","img");
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Champion frisselame") {
			Card min = new Minion(2,3,4,"Champion frisselame","description","img",false,true,true);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Bénédiction de puissance") {
			Card min = new Spell(1,"Bénédiction de puissance","description","img");
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Consécration") {
			Card min = new Spell(4,"Consécration","description","img");
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Tourbillon") {
			Card min = new Spell(1,"Tourbillon","description","img");
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Avocat commis d'office") {
			Card min = new Minion(7,0,2,"Avocat commis d'office","description","img",true,false,false);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Maîtrise du blocage") {
			Card min = new Spell(3,"Maîtrise du blocage","description","img");
			hand.add(min);
			return min;
		}
		return null;
	}
	
	public abstract void power();
}
