package com.miniheartstone.engine;
import java.util.ArrayList;
import java.util.UUID;

public class Hero {
		static int MANA_MAX= 10;
		protected int mana;
		protected UUID HeroUUID;
		protected String description;
		
		protected String HeroName;
		protected int health;
		protected int armor;
		//liste tte les carte que le heros peut avoir a recuperer dans la base de donn√©es.
		protected ArrayList<Card> deck = new ArrayList<Card>();
		protected ArrayList<Card> hand = new ArrayList<Card>();
		protected ArrayList<Card> board = new ArrayList<Card>();
		
		protected Hero(String HeroName, int health, String description) {
			this.HeroName= HeroName;
			this.health=health;
			this.description=description;
			this.mana=0;
			this.armor =0;
			this.HeroUUID = UUID.randomUUID();
			// ici aller chercher dnas Spring pour remplier Deck
			
			Card lel = new Minion(1, 0,1, "carte1", "je suis n1", null,false, false, true);
			this.deck.add(lel);

			Card lal = new Minion(2, 0,2, "Chef de raid", "je suis n2", null,false, false, false);
			this.deck.add(lal);

			Card lul = new Minion(3, 0,3, "carte3", "je suis n3", null,false, true, false);
			this.deck.add(lul);

			Card lol = new Minion(4, 0,4, "Chef de raid", "je suis n4", null,true, false, false);
			this.deck.add(lol);
		}
		
		//public abstract void power();
		
		public void draw() {
			int rd = (int)(Math.random() * (deck.size()));
			hand.add((deck.get(rd)).cloneCard());
		}
		
		public void invock(UUID cardID) {
			if(isOnMyHand (cardID)) {
				if(this.getCardFromHandByUUID(cardID) instanceof Minion ) {
					
					Card card = this.getCardFromHandByUUID(cardID);
					hand.remove(card);
					// si ma carte est un chef de raid +1 att a toutes les cretures du board
					if (card.getName() == "Chef de raid") {
						for(int i =0; i< board.size(); i++) {
							Card miniminion = this.getBoard().get(i);
							((Minion)miniminion).setAttack(((Minion)miniminion).getAttack()+1);
							
						}
					}
					// ajout des pts d'attack en fonction du nombre de chef de raids presents sur le plateau alliÈ
					((Minion)card).setAttack(((Minion)card).getAttack()+ this.howManyChefDeRaidInMyBoard());
					board.add(card);
					
					// si la carte ‡ charge
					if(((Minion)card).getHasCharge()) {
						((Minion)card).setReadyToAttack(true);
					}
				}
				else {
					
				}
			}
		}
		
		// retourne true si la carte est dans la main, fasle sinon
		public boolean isOnMyHand (UUID cardID) {
			boolean present = false;
			for (Card card : hand) {
			    if (card.getCardUUID() == cardID) {
			    	present = true;
			    	break;
			    }
			}
			return present;
		}
		
		// retourne la card de la main en fonction d'un UUID ou null si elle n'est pas dans la main du joueur
		public Card getCardFromHandByUUID (UUID cardID) {
			for (Card card : hand) {
				if (card.getCardUUID() == cardID) {
					return card;
				}
			}
			return null;
		}
		
		public int howManyChefDeRaidInMyBoard() {
			int count = 0;
			for(int i =0; i< board.size(); i++) {
				if(this.getBoard().get(i).getName() == "Chef de raid") {
					count++;
				}
			}
			return count;
		}
		
		public String getHeroName() {
			return this.HeroName;
		}
		
		public int getHealth() {
			return this.health;
		}
		
		public ArrayList<Card> getDeck() {
			return this.deck;
		}
		
		public ArrayList<Card> getHand() {
			return this.hand;
		}
		
		public ArrayList<Card> getBoard() {
			return this.board;
		}
		
		public void setHealth(int health) {
			this.health = health;
		}
		
		public int getMana() {
			return this.mana;
		}
		
		public void setMana(int mana) {
			this.mana = mana;
		}
		
		public int getArmor() {
			return this.armor;
		}
		
		public String toString() {
			String affHand = "\n"+"\n"+ "------------- affichage de la main courant ------------- \n";
			for(int i = 0; i < this.hand.size(); i++) {
				affHand = affHand + "------------- affichage de la main courant ------------- \n";
	 			affHand = affHand +"\n …lÈment ‡ l'index " + i + " \n " + hand.get(i).toString()+" \n ";
	 		}
			
			return "mana : "+this.mana+"\n"+
					"HeroUUID : "+this.HeroUUID+"\n"+
					"description :"+this.description+"\n"+
					"HeroName : "+this.HeroName+"\n"+
					"health : "+this.health+"\n"+
					"armor : "+this.armor+affHand;
					
		}
		
		public String superToString() {
			String aff =    "					 Hero PV					\n"
							+"					|   "+this.getHealth()+"   |					\n"
							+"					----------					\n";
			
			aff = aff + "Cartes en main : \n";
			for (int i =0; i< hand.size(); i++ ) {
				aff = aff + "|||"+hand.get(i).getName()+" Mana Cost : " +hand.get(i).getManaCost()+"|||	";
				
			}
			aff = aff + "\n";
			
			aff = aff + "Cartes en du board : \n";
			for (int i =0; i< board.size(); i++ ) {
				aff = aff + "|||"+board.get(i).getName()+"  nb attack ? : " +((Minion)board.get(i)).getAttack()+"|||	";
				
			}
			
			aff = aff + "\n";
			return aff;
		}
			
		public static void main(String[] args) {
			Hero her = new Hero("Louis le bg", 30, "le plus fort");
			her.draw();
			her.draw();
			her.draw();
			
			her.invock(her.getHand().get(0).getCardUUID());
			her.invock(her.getHand().get(0).getCardUUID());
			
			
			System.out.println(her.superToString());
		}
}
