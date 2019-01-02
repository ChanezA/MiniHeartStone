package engine;

import java.util.ArrayList;
import java.util.UUID;

import exception.MiniHeartStoneException;

public abstract class Hero_Doublure {
		static int MANA_MAX= 10;
		protected int mana;
		protected UUID HeroUUID;
		protected String description;
		
		protected String HeroName;
		protected int health;
		protected int armor;
		//liste tte les carte que le heros peut avoir a recuperer dans la base de données.
		protected ArrayList<AbstractCard> deck = new ArrayList<AbstractCard>();
		protected ArrayList<AbstractCard> hand = new ArrayList<AbstractCard>();
		protected ArrayList<AbstractCard> board = new ArrayList<AbstractCard>();
		
		protected boolean looser = false;
		protected boolean winner = false;

		protected Hero_Doublure(String HeroName, int health, String description) {
			this.HeroName= HeroName;
			this.health=health;
			this.description=description;
			this.mana=0;
			this.armor =0;
			this.HeroUUID = UUID.randomUUID();
			// ici aller chercher dnas Spring pour remplier Deck
			
			AbstractCard lel = new Minion("Sanglier de brocheroc", "je suis n1",1, 1, 1, false,false, false, null);
			this.deck.add(lel);
			//						vie, attack , mana				provo life steal charge
			AbstractCard lal = new Minion("Chevaucheur de loup", "je suis n2",3, 1, 3, false,false, false, null);
			this.deck.add(lal);

			AbstractCard lul = new Minion("Chef de raid", "je suis fort",3, 1, 2, false,false, true, null);
			this.deck.add(lul);

			AbstractCard lol = new Minion("Yéti noroit", "je suis n4",4, 1, 2, false,true, false, null);
			this.deck.add(lol);
			
			AbstractCard lil = new Minion("Soldat du compté d'or", "je suis n4",1, 1, 2, false,true, false, null);
			this.deck.add(lil);
			
			AbstractCard lzl = new Spell(1, "Image miroir", "je suis un spell qui invoque 2 0/2 provoc", null);
			this.deck.add(lzl);
			
			AbstractCard lyl = new Spell(3, "Maîtrise du blocage", "je suis un spell qui pioche", null);
			this.deck.add(lyl);
			
		}
		
		public abstract void power();
		
		public AbstractCard draw() {
			int rd = (int)(Math.random() * (deck.size()));
			AbstractCard crd = null;
			try { crd = deck.get(rd).cloneCard(); }
			catch (Exception e) { System.out.println(e.getMessage()); }
			hand.add(crd);
			return crd;
		}
		
		/*
		 * Invoque une créature ou un spell, seul les spells Image miroir et maitrise du blocage sont gerrés ici
		 * peut lancer l'exception pas le mana suffisant et l'exception cette carte n'est pas dans ta main
		 */
		public void invock(UUID cardID) {
			try {
				// si la carte est bien en main
				if(this.isOnMyHand(cardID)){
					// si le joueur à le mana suffisant
					if (this.getCardFromHandByUUID(cardID).getManaCost() <= this.getMana()) {
						// on retire le mana au joueur
						this.setMana(this.getMana() - this.getCardFromHandByUUID(cardID).getManaCost());
						// si c'est un minion
						if(this.getCardFromHandByUUID(cardID) instanceof Minion ) {
							//System.out.println("exsque je passe ici lele�k�eckpaekcp");
							AbstractCard abstractCard = this.getCardFromHandByUUID(cardID);
							hand.remove(abstractCard);
							// si ma carte est un chef de raid +1 att a toutes les cretures du board
							if (abstractCard.getName() == "Chef de raid") {
								for(int i =0; i< board.size(); i++) {
									AbstractCard miniminion = this.getBoard().get(i);
									((Minion)miniminion).setAttack(((Minion)miniminion).getAttack()+1);
		
								}
							}
							// ajout des pts d'attack en fonction du nombre de chef de raids presents sur le plateau alli�
							((Minion) abstractCard).setAttack(((Minion) abstractCard).getAttack()+ this.howManyChefDeRaidInMyBoard());
							board.add(abstractCard);
		
							// si la carte � charge
							if(((Minion) abstractCard).getHasCharge()) {
								((Minion) abstractCard).setReadyToAttack(true);
							}
						}
						//si c'est un spell
						else {
							//System.out.println("et la ");
							Spell spell = (Spell)(this.getCardFromHandByUUID(cardID));
		
							// si c'est image miroir
							if (spell.getName() == "Image miroir") {
								AbstractCard one =new Minion("Soldat", "je suis n4",1, 0, 2, true,false, false, null);
								((Minion)one).setAttack(((Minion)one).getAttack()+ this.howManyChefDeRaidInMyBoard());
								AbstractCard two =new Minion("Soldat", "je suis n4",1, 0, 2, true,false, false, null);
								((Minion)two).setAttack(((Minion)two).getAttack()+ this.howManyChefDeRaidInMyBoard());
								this.getBoard().add(one);
								this.getBoard().add(two);
		
								this.getHand().remove(this.getCardFromHandByUUID(cardID));
							}
							// si c'est maitrise du blocage
							if(spell.getName() == "Maîtrise du blocage") {
								this.getHand().remove(this.getCardFromHandByUUID(cardID));
								this.setArmor(this.getArmor()+5);
								this.draw();
							}
						}
					}
					// si le joueur n'a pas le mana suffisant
					else {
						throw new MiniHeartStoneException("Vous n'avez pas le mana suffisant");
					}
				}
				// si la carte n'est pas en main
				else {
					throw new MiniHeartStoneException("Vous n'avez pas cette carte en main");
				}
			}// fin du try
			catch(MiniHeartStoneException e ) {
				System.out.println(e.toString());
			}
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
			try {
				// si la créature est bien sur mon board
				if(this.isOnMyBoard(carteAttaqueeUUID)) {
					Minion min = (Minion)(this.getCardFromBoardByUUID(carteAttaqueeUUID));
					min.setLife(min.getLife()-degats);
					
					if (min.getLife() <= 0) {
						this.getBoard().remove(min);
						if (min.getName() == "Chef de raid") {
							for(int i =0; i< board.size(); i++) {
								AbstractCard miniminion = this.getBoard().get(i);
								((Minion)miniminion).setAttack(((Minion)miniminion).getAttack()-1);
							}
						}
					}
				}
				// si la créature n'est pas sur mon board
				else {
					throw new MiniHeartStoneException("Cette créature n'est pas sur le borad adverse");
				}
			}
			catch(MiniHeartStoneException e) {
				System.out.println(e.toString());
			}
		}
		
		// retourne true si la carte est dans la main, fasle sinon
		public boolean isOnMyHand (UUID cardID) {
			boolean present = false;
			for (AbstractCard abstractCard : hand) {
			    if (abstractCard.getCardUUID() == cardID) {
			    	present = true;
			    	break;
			    }
			}
			return present;
		}
		
		public boolean isOnMyBoard (UUID cardID) {
			boolean present = false;
			for (AbstractCard abstractCard : board) {
			    if (abstractCard.getCardUUID() == cardID) {
			    	present = true;
			    	break;
			    }
			}
			return present;
		}
		
		// retourne la card de la main en fonction d'un UUID ou null si elle n'est pas dans la main du joueur
		public AbstractCard getCardFromHandByUUID (UUID cardID) {
			for (AbstractCard abstractCard : hand) {
				if (abstractCard.getCardUUID() == cardID) {
					return abstractCard;
				}
			}
			return null;
		}
		
		public AbstractCard getCardFromBoardByUUID (UUID cardID) {
			for (AbstractCard abstractCard : board) {
				if (abstractCard.getCardUUID() == cardID) {
					return abstractCard;
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
		
		public ArrayList<AbstractCard> getDeck() {
			return this.deck;
		}
		
		public ArrayList<AbstractCard> getHand() {
			return this.hand;
		}
		
		public ArrayList<AbstractCard> getBoard() {
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
		
		public boolean getLooser() {
			return this.looser;
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
			String aff =
							"					|  PV : "+this.getHealth()+"  AR : "+ this.getArmor()+"  MN : "+this.getMana()+"  |					\n"
							+"					------------------------------					\n";
			
			aff = aff + "Cartes en main : \n";
			for (int i =0; i< hand.size(); i++ ) {
				aff = aff + "|||"+hand.get(i).getName()+" Mana Cost : " +hand.get(i).getManaCost()+"|||	";
				
			}
			aff = aff + "\n";
			
			aff = aff + "Cartes en du board : \n";
			for (int i =0; i< board.size(); i++ ) {
				aff = aff + "|||"+board.get(i).getName()+"	lf : " +((Minion)board.get(i)).getLife()+ " Att : " +((Minion)board.get(i)).getAttack()+"|||	";
				
			}
			
			aff = aff + "\n";
			return aff;
		}
		public AbstractCard draw(String cardName) {
			if(cardName == "Sanglier brocheroc") {
				AbstractCard min = new Minion("Sanglier de brocheroc", "je suis n1",1, 1, 1, false,false, false, null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Soldat du comté-de-l'or") {
				AbstractCard min = new Minion("Soldat du compté d'or", "je suis n4",1, 1, 2, false,true, false, null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Chevaucheur de loup") {
				AbstractCard min = new Minion("Chevaucheur de loup", "je suis n2",3, 1, 3, false,false, false, null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Chef de raid") {
				AbstractCard min = new Minion("Chef de raid", "je suis fort",3, 2, 2, false,false, true, null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Yéti noroit") {
				AbstractCard min = new Minion("Yéti noroit", "je suis n4",4, 4, 5, false,true, false, null);
				hand.add(min);
				return min;
			}
			
			// Spell(int manaCost, String name, String description, String pictureURL)
			else if(cardName == "Image miroir") {
				AbstractCard min = new Spell(1,"Image miroir","description","img");
				hand.add(min);
				return min;
			}
			
			// Spell(int manaCost, String name, String description, String pictureURL)
			else if(cardName == "Explosion des arcanes") {
				AbstractCard min = new Spell(2,"Explosion des arcanes","description","img");
				hand.add(min);
				return min;
			}
			
			// Spell(int manaCost, String name, String description, String pictureURL)
			else if(cardName == "Métamorphose") {
				AbstractCard min = new Spell(4,"Métamorphose","description","img");
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Champion frisselame") {
				AbstractCard min = new Minion("Champion frisselame","description",4,2,8,false,false,true,null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Bénédiction de puissance") {
				AbstractCard min = new Spell(1,"Bénédiction de puissance","description","img");
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Consécration") {
				AbstractCard min = new Spell(4,"Consécration","description","img");
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Tourbillon") {
				AbstractCard min = new Spell(1,"Tourbillon","description","img");
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Avocat commis d'office") {
				AbstractCard min = new Minion("Avocat commis d'office","description",2,0,7,false,true,false,null);
				hand.add(min);
				return min;
			}
			
			else if(cardName == "Maîtrise du blocage") {
				AbstractCard min = new Spell(3,"Maîtrise du blocage","description","img");
				hand.add(min);
				return min;
			}
			return null;
		}
}
