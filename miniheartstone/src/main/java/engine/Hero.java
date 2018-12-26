package engine;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Hero {
		static int MANA_MAX= 10;
		protected int mana;
		protected UUID HeroUUID;
		protected String description;
		
		protected String HeroName;
		protected int health;
		protected int armor;
		//liste tte les carte que le heros peut avoir a recuperer dans la base de données.
		protected ArrayList<Card> deck = new ArrayList<Card>();
		protected ArrayList<Card> hand = new ArrayList<Card>();
		protected ArrayList<Card> board = new ArrayList<Card>();
		
		protected boolean looser = false;
		protected boolean winner = false;
		
		protected Hero(String HeroName, int health, String description) {
			this.HeroName= HeroName;
			this.health=health;
			this.description=description;
			this.mana=0;
			this.armor =0;
			this.HeroUUID = UUID.randomUUID();
			// ici aller chercher dnas Spring pour remplier Deck
			
			Card lel = new Minion(1, 1,1, "Sanglier de brocheroc", "je suis n1", null,false, false, false);
			this.deck.add(lel);
			//						vie, attack , mana				provo life steal charge
			Card lal = new Minion(1, 3,3, "Chevaucheur de loup", "je suis n2", null,false, false, true);
			this.deck.add(lal);

			Card lul = new Minion(2, 2,3, "Chef de raid", "je suis fort", null,false, true, false);
			this.deck.add(lul);

			Card lol = new Minion(5, 4,4, "Y�ti noroit", "je suis n4", null,true, false, false);
			this.deck.add(lol);
			
			Card lil = new Minion(2, 1,1, "Soldat du compt� d'or", "je suis n4", null,true, false, false);
			this.deck.add(lil);
			
			Card lzl = new Spell(1, "Image miroir", "je suis un spell qui invoque 2 0/2 provoc", null);
			this.deck.add(lzl);
			
			Card lyl = new Spell(3, "Ma�trise du blocage", "je suis un spell qui pioche", null);
			this.deck.add(lyl);
			
		}
		
		public abstract void power();
		
		public void draw() {
			int rd = (int)(Math.random() * (deck.size()));
			hand.add((deck.get(rd)).cloneCard());
		}
		
		public boolean invock(UUID cardID) {
			boolean isInvock = false;
			if(this.isOnMyHand(cardID) && this.getCardFromHandByUUID(cardID).getManaCost() <= this.getMana()) {
				// on retire le mana au joueur
				this.setMana(this.getMana() - this.getCardFromHandByUUID(cardID).getManaCost());
				// si c'est un minion
				if(this.getCardFromHandByUUID(cardID) instanceof Minion ) {
					//System.out.println("exsque je passe ici lele�k�eckpaekcp");
					Card card = this.getCardFromHandByUUID(cardID);
					hand.remove(card);
					// si ma carte est un chef de raid +1 att a toutes les cretures du board
					if (card.getName() == "Chef de raid") {
						for(int i =0; i< board.size(); i++) {
							Card miniminion = this.getBoard().get(i);
							((Minion)miniminion).setAttack(((Minion)miniminion).getAttack()+1);
							
						}
					}
					// ajout des pts d'attack en fonction du nombre de chef de raids presents sur le plateau alli�
					((Minion)card).setAttack(((Minion)card).getAttack()+ this.howManyChefDeRaidInMyBoard());
					board.add(card);
					
					// si la carte � charge
					if(((Minion)card).getHasCharge()) {
						((Minion)card).setReadyToAttack(true);
					}
				}
				//si c'est un spell
				else {
					//System.out.println("et la ");
					Spell spell = (Spell)(this.getCardFromHandByUUID(cardID));
					
					// si c'est image miroir
					if (spell.getName() == "Image miroir") {
						Card one =new Minion(2, 0,0, "Serviteurs", "je suis invoque par img mir", null,true, false, false);
						((Minion)one).setAttack(((Minion)one).getAttack()+ this.howManyChefDeRaidInMyBoard());
						Card two =new Minion(2, 0,0, "Serviteurs", "je suis invoque par img mir", null,true, false, false);
						((Minion)two).setAttack(((Minion)two).getAttack()+ this.howManyChefDeRaidInMyBoard());
						this.getBoard().add(one);
						this.getBoard().add(two);
						
						this.getHand().remove(this.getCardFromHandByUUID(cardID));
					}
					if(spell.getName() == "Ma�trise du blocage") {
						this.getHand().remove(this.getCardFromHandByUUID(cardID));
						this.setArmor(this.getArmor()+5);
						this.draw();
					}
				}
			}
			return isInvock;
		}
		
		public void myHeroHasBeenAttack(int degats) {
			// si j'ai de l'armure
			if(this.getArmor() > 0) {
				int diff = this.getArmor()-degats;
				// si les degats son supp�rieur � l'armure
				if(diff < 0) {
					this.setArmor(0);
					// je fais + car diff est ici n�gatif
					this.setHealth(this.getHealth()+ diff);
				}
				// si l'armure suffit
				else {
					this.setArmor(diff);
				}
			}
			// si je n'ai pas d'armur
			else{
				this.setHealth(this.getHealth()-degats);
			}
			// je v�rifie que le hero n'est pas mort
			if (this.getHealth() <=0) {
				looser = true;
			}
		}
		
		public void hasBeenAttack(UUID carteAttaqueeUUID, int degats) {
			if(this.isOnMyHand(carteAttaqueeUUID)) {
				Minion min = (Minion)(this.getCardFromHandByUUID(carteAttaqueeUUID));
				min.setLife(min.getLife()-degats);
				
				if (min.getName() == "Chef de raid") {
					this.getBoard().remove(min);
				}
				
				if (min.getLife() <= 0) {
					this.getBoard().remove(min);
					if (min.getName() == "Chef de raid") {
						for(int i =0; i< board.size(); i++) {
							Card miniminion = this.getBoard().get(i);
							((Minion)miniminion).setAttack(((Minion)miniminion).getAttack()-1);
						}
					}
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
		
		public boolean isOnMyBoard (UUID cardID) {
			boolean present = false;
			for (Card card : board) {
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
		
		public Card getCardFromBoardByUUID (UUID cardID) {
			for (Card card : board) {
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
		
		public boolean aCardWithProvocationInMyBorad() {
			for(int i =0; i< board.size(); i++) {
				if(((Minion)this.getBoard().get(i)).getHasPrococation()) {
					return true;
				}
			}
			return false;
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
			if(health >= 30) {
				this.health = 30;
			}
			else {
				this.health = health;
			}
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
		
		public void setArmor(int num) {
			this.armor = num;
		}
		
		public String toString() {
			String affHand = "\n"+"\n"+ "------------- affichage de la main courant ------------- \n";
			for(int i = 0; i < this.hand.size(); i++) {
				affHand = affHand + "------------- affichage de la main courant ------------- \n";
	 			affHand = affHand +"\n �l�ment � l'index " + i + " \n " + hand.get(i).toString()+" \n ";
	 		}
			
			return "mana : "+this.mana+"\n"+
					"HeroUUID : "+this.HeroUUID+"\n"+
					"description :"+this.description+"\n"+
					"HeroName : "+this.HeroName+"\n"+
					"health : "+this.health+"\n"+
					"armor : "+this.armor+affHand;
					
		}
		
		public String superToString() {
			String aff =    "					 PV Armor					\n"
							+"					|   "+this.getHealth()+"  "+ this.getArmor()+"  |					\n"
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
			/*Hero her = new Hero("Louis le bg", 30, "le plus fort");
			her.draw();
			her.draw();
			her.draw();
			her.draw();
			her.draw();
			her.draw();
			
			her.setMana(20);
			her.invock(her.getHand().get(0).getCardUUID());
			her.invock(her.getHand().get(0).getCardUUID());
			her.invock(her.getHand().get(0).getCardUUID());
			her.invock(her.getHand().get(0).getCardUUID());
			
			
			System.out.println(her.superToString());
			System.out.println(her.getMana());*/
		}
}
