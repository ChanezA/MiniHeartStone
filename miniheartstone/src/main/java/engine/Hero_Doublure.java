package engine;
//test test test
public abstract class Hero_Doublure extends Hero {
	
	public Hero_Doublure(String HeroName, int health, String description) {
		super(HeroName,health,description);
	}
	
	public Card draw(String cardName) {
		if(cardName == "Sanglier brocheroc") {
			Card min = new Minion("Sanglier de brocheroc", "je suis n1",1, 1, 1, false,false, false, null);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Soldat du comté-de-l'or") {
			Card min = new Minion("Soldat du compté d'or", "je suis n4",1, 1, 2, false,true, false, null);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Chevaucheur de loup") {
			Card min = new Minion("Chevaucheur de loup", "je suis n2",3, 1, 3, false,false, false, null);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Chef de raid") {
			Card min = new Minion("Chef de raid", "je suis fort",3, 2, 2, false,false, true, null);
			hand.add(min);
			return min;
		}
		
		else if(cardName == "Yéti noroit") {
			Card min = new Minion("Yéti noroit", "je suis n4",4, 1, 2, false,true, false, null);
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
			Card min = new Minion("Champion frisselame","description",4,2,8,false,false,true,null);
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
			Card min = new Minion("Avocat commis d'office","description",2,0,7,false,true,false,null);
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
