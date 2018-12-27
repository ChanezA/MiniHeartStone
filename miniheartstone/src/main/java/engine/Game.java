package engine;

import java.util.ArrayList;
import java.util.UUID;


public class Game {

    // Private attributes
    protected Player currentPlayer;
    protected Player notCurrentPlayer;
    protected Player player1;
    protected Player player2;
    
    protected String iAmWaitingFor = "";
    protected UUID tmpUUID = null;
    protected boolean HeroicPowerHasBeenUse = false;
    
    protected UUID gameID;

    public static int MANA_MAX = 10;

    // Constructor
    public Game(Player player1, Player player2) {
        this.player1  = player1;
        this.player2 = player2;
        
        UUID gameID = UUID.randomUUID();
        
        this.initGame();
    }

    // Getters
    public Player getCurrentPlayer() {
    	return this.currentPlayer;
    }
    public Player getNotCurrentPlayer() {
        return this.notCurrentPlayer;
    }
    public Player getPlayer1() { return this.player1; }
    public Player getPlayer2() { return this.player2; }
    
    public UUID getGameID () {return this.gameID;}
    
    //Setters
    public void setCurrentPlayer(Player player) {
    	this.currentPlayer = player;
    }
    public void setNotCurrentPlayer(Player player) {
    	this.notCurrentPlayer = player;
    }

    /**
     * Initializes the game
     */
    private void initGame() {
        this.currentPlayer = player1;
        this.notCurrentPlayer = player2;
        int i;
        for (i = 0; i < 4; i++) {
            player1.getHero().draw();
            player2.getHero().draw();
        }
        this.getCurrentPlayer().getHero().setMana(1);
        this.getNotCurrentPlayer().getHero().setMana(0);
    }

    /**
     * passage de tour
     */
    private void passTurn(UUID playerID) {
    	if(this.CurrentPlayerOrNot(playerID)) {
	    	// echange le joueur courant avec le non courant
	        Player tmp = this.getCurrentPlayer();
	        this.setCurrentPlayer(this.getNotCurrentPlayer());
	        this.setNotCurrentPlayer(tmp);
	        HeroicPowerHasBeenUse = false;
	        
	        // incr�mentation du mana pour le nouveau joueur courant si besoin
	        if(this.getCurrentPlayer().getHero().getMana() < MANA_MAX) {
	        	this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()+1);
	        }
	        
	        // on remet toutes les cr�atures du joueru courant ready to attack
	        for(int i = 0; i<this.getCurrentPlayer().getHero().getBoard().size(); i++) {
	        	Minion min =(Minion)(this.getCurrentPlayer().getHero().getBoard().get(i));
	        	min.setReadyToAttack(true);
	        }
	
	        this.getCurrentPlayer().getHero().draw();
    	}
    }
    
    // retourne vrai si le joueur pass� en param est courant ou faux pour tout le reste
    public boolean CurrentPlayerOrNot(UUID playerUUID) {
    	boolean crt = false;
    	if(this.getCurrentPlayer().getPlayerID() == playerUUID) {
    		crt = true;
    	}
    	return crt;
    }

    /**
     * Returns the board of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public ArrayList<Card> getMyBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer1().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer2().getHero().getBoard();
    	}
    }

    /**
     * Returns the board the opponent of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public ArrayList<Card> getOpponentBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer2().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer1().getHero().getBoard();
    	}
    }
    
    public void invock(UUID playerUUID, UUID cardID) {
    	// si tu es bien le joueur courant et que la carte est bien dans ta main et que tu as bien le mana necessaire
    	if(this.CurrentPlayerOrNot(playerUUID) && this.getCurrentPlayer().getHero().isOnMyHand(cardID) && this.getCurrentPlayer().getHero().getMana() >= this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getManaCost()) {
    		
    		// si cette carte est un minion
    		if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Minion) {
    			this.getCurrentPlayer().getHero().invock(cardID);
    		}
    		// si c'est un spell
    		else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Spell) {
    			// si ce n'est pas un spell qui affecte l'enemi ni un spell qui necessite un siblage
    			if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Image miroir"
    					|| this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Ma�trise du blocage") {
    				this.getCurrentPlayer().getHero().invock(cardID);
    			}
    			
    			// si c'est un spell c'est tourbilol
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Tourbillon") {
    				// on retire 1 pv a toutes les invocations des bord des deux joueurs
    				for(int i=0; i<this.getCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getCurrentPlayer().getHero().getBoard().get(i));
    					this.getCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en cons�quence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
    			}
    			
    			// si c'est le spell cons�cration
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Cons�cration") {
    				// retire 2 pdv a tous les minions adverse
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 2);
    				}
    				// degats sur le joueur adverse
    				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(2);
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en cons�quence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
    			}
    			
    			// si le spell c'est explosion des arcanes
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Explosion des arcanes") {
    				// retire 1 pdv a tous les minions adverse
    				for(int i=0; i<this.getNotCurrentPlayer().getHero().getBoard().size(); i++) {
    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
    				}
    				// on retire la carte de la main du joueur
    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
    				// on lui retire le mana en cons�quence
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
    			}
    			// si le spell c'est M�tamorphose
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "M�tamorphose") {
    				iAmWaitingFor = "M�tamorphose";
    				this.tmpUUID = cardID;
    			}
    			// si le spell c'est B�n�diction de puissance
    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "B�n�diction de puissance") {
    				iAmWaitingFor = "B�n�diction de puissance";
    				this.tmpUUID = cardID;
    			}
    		}
    	}
    }
    
    // selection d'un minion ou d'un hero pour spell pouvoirs h�roiques etc ...
    public void select(UUID playerUUID, UUID ennemyUUID) {
    	
    	if (this.iAmWaitingFor == "M�tamorphose" && playerUUID == this.getCurrentPlayer().getPlayerID()) {
    		// si on cible une cr�ature du board adverse
    		if(this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
    			Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
    			// la fameuse moutonification
    			min.setAttack(1);
    			min.setLife(1);
    			min.setManaCost(1);
    			min.setName("Mouton");
    			min.setDescription("mdr les moutons c con");
    			min.setPictureURL("null"); // � modif quand on aura des immages si on en a un jrs
    			min.setHasCharge(false);
    			min.setHasVolDeVie(false);
    			min.setHasProvocation(false);
    			
    			// gestion de base apr�s avoir play une carte
    			// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
				// on lui retire le mana en cons�quence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
				
				this.iAmWaitingFor = "";
				this.tmpUUID = null;
    			
    		}
    	}
    	else if (this.iAmWaitingFor == "B�n�diction de puissance" && playerUUID == this.getCurrentPlayer().getPlayerID()) {
    		// si la cr�ature est sur le board adverse
    		if(this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
    			Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
    			min.setAttack(min.getAttack()+3);
    			
    			// gestion de base apr�s avoir play une carte
    			// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
				// on lui retire le mana en cons�quence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
				
				this.iAmWaitingFor = "";
				this.tmpUUID = null;
    		}
    		// si la cr�ature est sur notre board
    		else if(this.getCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
    			Minion min = (Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
    			min.setAttack(min.getAttack()+3);
    			
    			// gestion de base apr�s avoir play une carte
    			// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
				// on lui retire le mana en cons�quence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
				
				this.iAmWaitingFor = "";
				this.tmpUUID = null;
    		}
    	}
    	// si on attend pour le pouvoir du mage
    	else if (this.iAmWaitingFor == "Pouvoir du mage"+this.getCurrentPlayer().getPlayerID().toString() && playerUUID == this.getCurrentPlayer().getPlayerID()) {
    		// si tu as le mana suffisant pour jouer le pouvoir
    		if(this.getCurrentPlayer().getHero().getMana()>=2) {
    			// si la seclection est le joueur adverse
    			if(this.getNotCurrentPlayer().getPlayerID() == tmpUUID) {
    				// on applique les degats au hero adverse
    				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(1);
    				// on retire le prix du pouvoir h�roique ici 2
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
    				
    				this.iAmWaitingFor = "";
    				this.tmpUUID = null;
    				HeroicPowerHasBeenUse = true;
    			}
    			// si c'est une cr�ature adverse
    			else if(this.getNotCurrentPlayer().getHero().isOnMyBoard(tmpUUID)) {
    				this.getNotCurrentPlayer().getHero().hasBeenAttack(tmpUUID,1);
    				// on retire le prix du pouvoir h�roique ici 2
    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
    				
    				this.iAmWaitingFor = "";
    				this.tmpUUID = null;
    				HeroicPowerHasBeenUse = true;
    			}
    		}
    	}
    }
	public void power(UUID playerUUID,UUID selectCardUUID) {
		// si le joueur est bien le joueur courant
		if(playerUUID == this.getCurrentPlayer().getPlayerID()) {
			// si tu as le mana suffisant pour jouer le pouvoir et que tu n'as pas deja use ton pouvoir 
    		if(this.getCurrentPlayer().getHero().getMana()>=2 && HeroicPowerHasBeenUse == false) {
				// si c'est un mage 
				if (this.getCurrentPlayer().getHero().getHeroName() == "Mage") {
					iAmWaitingFor = "Pouvoir du mage"+this.getCurrentPlayer().getPlayerID().toString();
	    			this.tmpUUID = selectCardUUID;
				}
				// si c'est un guerrier ou palladin
				else{
					this.getCurrentPlayer().getHero().power();
					this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
					
					// le pouvoir heroic est now utilis� pour ce tour;
					HeroicPowerHasBeenUse = true;
				}
    		}
		}
	}
	
	public void attack(UUID myCardUUID, UUID opponentUUID) {
		// on v�rifie que les deux cr�atures �xixtent bien
		if(this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getHero().isOnMyBoard(opponentUUID)){
			// on v�rifie que ma cr�ature n'a pas d�ja attaqu�
			if (((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID))).getReadyToAttack() == true) {
				Minion monMin = ((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
				Minion oppMin = ((Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(opponentUUID)));
				// si une cr�ature adverse � provovation
				if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad() && oppMin.getHasPrococation()) {
					// on fais les degats sur les deux minions oklm
					this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
					this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
					monMin.setReadyToAttack(false);
					// si ma cr�ature � du vol de vie
					if(monMin.getHasVolDeVie()) {
						// on augmente les pv du hero
						this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth()+monMin.getAttack());
					}
				}
				// si il n'y a pas de provoc en face
				else {
					// on fais les degats sur les deux minions oklm
					this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
					this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
					monMin.setReadyToAttack(false);
					// si ma cr�ature � du vol de vie
					if(monMin.getHasVolDeVie()) {
						// on augmente les pv du hero
						this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth()+monMin.getAttack());
					}
				}
			}
		}
		// si ma créature existe bien sur mon board et que j'ai sélectionné le joueur adverse
		else if (this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getPlayerID() == opponentUUID) {
			Minion monMin = ((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
			
			// on fais les degats sur l'adversaire
			this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(monMin.getAttack());
			monMin.setReadyToAttack(false);
			// si ma créature du vol de vie
			if(monMin.getHasVolDeVie()) {
				// on augmente les pv du hero
				this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth()+monMin.getAttack());
			}
		}
	}
	
	
	
	public String toString() {
		String aff = "					"+this.getPlayer1().getName()+"	"+this.getPlayer1().getHero().getHeroName()+"\n";
		aff = aff+"					-----------------------------					\n";
		aff = aff+"					|  PV : "+this.getPlayer1().getHero().getHealth()+"  AR : "+ this.getPlayer1().getHero().getArmor()+"  MN : "+this.getPlayer1().getHero().getMana()+"  |					\n"
			+"					-----------------------------					\n";
	
		aff = aff + "Cartes en main : \n";
		
		// affichage de la main j1
		for (int i =0; i< player1.getHero().getHand().size(); i++ ) {
		aff = aff + "|||"+player1.getHero().getHand().get(i).getName()+" Mana Cost : " +player1.getHero().getHand().get(i).getManaCost()+"|||	";
		}
		aff = aff + "\n";
		
		// affichage du board j1
		aff = aff + "Cartes en du board : \n";
		for (int i =0; i< player1.getHero().getBoard().size(); i++ ) {
		aff = aff + "|||"+player1.getHero().getBoard().get(i).getName()+"	lf : " +((Minion)player1.getHero().getBoard().get(i)).getLife()+ " Att : " +((Minion)player1.getHero().getHand().get(i)).getAttack()+"|||	";
		}
		
		aff = aff + "\n";
		aff = aff + "***********************************************************************************************************************************************\n";
		aff = aff + "\n";
		
		// affichage du board j2
		aff = aff + "Cartes en du board : \n";
		for (int i =0; i< player2.getHero().getBoard().size(); i++ ) {
		aff = aff + "|||"+player2.getHero().getBoard().get(i).getName()+"	lf : " +((Minion)player2.getHero().getBoard().get(i)).getLife()+ " Att : " +((Minion)player1.getHero().getHand().get(i)).getAttack()+"|||	";
		}
		aff = aff + "\n";
		
		// affichage de la main j2
		aff = aff + "Cartes en main : \n";
		for (int i =0; i< player2.getHero().getHand().size(); i++ ) {
		aff = aff + "|||"+player2.getHero().getHand().get(i).getName()+" Mana Cost : " +player2.getHero().getHand().get(i).getManaCost()+"|||	";
		}
		aff = aff + "\n";
		
		aff = aff+"					-----------------------------					\n";
		aff = aff+"					|  PV : "+this.getPlayer2().getHero().getHealth()+"  AR : "+ this.getPlayer2().getHero().getArmor()+"  MN : "+this.getPlayer2().getHero().getMana()+"  |					\n"
			+"					-----------------------------					\n";
		aff = aff+ "					"+this.getPlayer2().getName()+"	"+this.getPlayer2().getHero().getHeroName()+"\n";
		
		
		return aff;
	}
	public static void main(String[] args) {
		
		Hero yoann = new Magus();
		Hero pierre = new Paladin();
		
		Player yoannTchoin = new Player("suce teub",yoann,3);
		Player pierreLaFouine = new Player("l'appel d'air",pierre,4);
		
		Game gm = new Game(yoannTchoin,pierreLaFouine);
		
		System.out.println(gm.toString());
	}
	
}
