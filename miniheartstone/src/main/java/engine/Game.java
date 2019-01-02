package engine;

import java.util.ArrayList;
import java.util.UUID;

import exception.MiniHeartStoneException;


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
        for (i = 0; i < 1; i++) {
        	/*
            player1.getHero().draw();
            player2.getHero().draw();
            */
        	player1.getHero().draw("Yéti noroit");
        	player2.getHero().draw("Yéti noroit");
        	
        	//player1.getHero().draw("Chef de raid");
        	//player2.getHero().draw("Chef de raid");
        	
        	player1.getHero().draw("Image miroir");
        	player2.getHero().draw("Image miroir");
        	
        	player1.getHero().draw("Métamorphose");
        	player2.getHero().draw("Métamorphose");
        	
        	//player1.getHero().draw("Explosion des arcanes");
        	//player2.getHero().draw("Explosion des arcanes");
        	
        }
        this.getCurrentPlayer().getHero().setMana(100);
        this.getNotCurrentPlayer().getHero().setMana(100);
    }

    /**
     * passage de tour
     */
    public void passTurn(UUID playerID) {
    	try {
	    	// si tu es le current player
	    	if(this.CurrentPlayerOrNot(playerID)) {
		    	// echange le joueur courant avec le non courant
		        Player tmp = this.getCurrentPlayer();
		        this.setCurrentPlayer(this.getNotCurrentPlayer());
		        this.setNotCurrentPlayer(tmp);
		        HeroicPowerHasBeenUse = false;
		        iAmWaitingFor = "";
		        
		        // incrémentation du mana pour le nouveau joueur courant si besoin
		        if(this.getCurrentPlayer().getHero().getMana() < MANA_MAX) {
		        	this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()+1);
		        }
		        
		        // on remet toutes les créatures du joueru courant ready to attack
		        for(int i = 0; i<this.getCurrentPlayer().getHero().getBoard().size(); i++) {
		        	Minion min =(Minion)(this.getCurrentPlayer().getHero().getBoard().get(i));
		        	min.setReadyToAttack(true);
		        }
		
		        this.getCurrentPlayer().getHero().draw();
	    	}
	    	else {
	    		throw new MiniHeartStoneException("Vous n'etes pas le joueur courant, vous ne pouvez pas passer");
	    	}
    	}
    	catch(MiniHeartStoneException e) {
    		System.out.println(e.toString());
    	}
    }
    
    // retourne vrai si le joueur passé en param est courant ou faux pour tout le reste
    public boolean CurrentPlayerOrNot(UUID playerUUID) {
    	boolean crt = false;
    	if(this.getCurrentPlayer().getPlayerID() == playerUUID) {
    		crt = true;
    	}
    	return crt;
    }

    /**
     * Returns the board of the given player
     * @param playerUUID
     * @return The board as a List<AbstractCard>
     */
    public ArrayList<AbstractCard> getMyBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer1().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer2().getHero().getBoard();
    	}
    }

    /**
     * Returns the board the opponent of the given player
     * @param playerUUID
     * @return The board as a List<AbstractCard>
     */
    public ArrayList<AbstractCard> getOpponentBoard(UUID playerUUID){
    	if (playerUUID == this.player1.getPlayerID() ) {
    		return this.getPlayer2().getHero().getBoard();
    	}
    	else {
    		return this.getPlayer1().getHero().getBoard();
    	}
    }
    
    public void invock(UUID playerUUID, UUID cardID) {
    	try {
    		iAmWaitingFor = "";
	    	// si tu es bien le joueur courant et que la carte est bien dans ta main et que tu as bien le mana necessaire
	    	if(this.CurrentPlayerOrNot(playerUUID) 
	    		&& this.getCurrentPlayer().getHero().isOnMyHand(cardID)
	    			&& this.getCurrentPlayer().getHero().getMana() >= this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getManaCost()) {
	    		
	    		// si cette carte est un minion
	    		if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Minion) {
	    			this.getCurrentPlayer().getHero().invock(cardID);
	    		}
	    		// si c'est un spell
	    		else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Spell) {
	    			// si ce n'est pas un spell qui affecte l'enemi ni un spell qui necessite un siblage
	    			if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Image miroir"
	    					|| this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Maétrise du blocage") {
	    				this.getCurrentPlayer().getHero().invock(cardID);
	    			}
	    			
	    			// si c'est un spell c'est tourbilol
	    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Tourbillon") {
	    				// on retire 1 pv a toutes les invocations des bord des deux joueurs
	    				for(int i=this.getCurrentPlayer().getHero().getBoard().size()-1; i>=0; i--) {
	    					Minion min = (Minion)(this.getCurrentPlayer().getHero().getBoard().get(i));
	    					this.getCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
	    				}
	    				for(int i=this.getNotCurrentPlayer().getHero().getBoard().size()-1; i>=0; i--) {
	    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
	    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
	    				}
	    				// on retire la carte de la main du joueur
	    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
	    				// on lui retire le mana en conséquence
	    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
	    			}
	    			
	    			// si c'est le spell consécration
	    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Consécration") {
	    				// retire 2 pdv a tous les minions adverse
	    				for(int i=this.getNotCurrentPlayer().getHero().getBoard().size()-1; i>=0; i--) {
	    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
	    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 2);
	    				}
	    				// degats sur le joueur adverse
	    				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(2);
	    				// on retire la carte de la main du joueur
	    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
	    				// on lui retire le mana en conséquence
	    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
	    			}
	    			
	    			// si le spell c'est explosion des arcanes
	    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Explosion des arcanes") {
	    				// retire 1 pdv a tous les minions adverse
	    				for(int i=this.getNotCurrentPlayer().getHero().getBoard().size()-1; i>=0; i--) {
	    					Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getBoard().get(i));
	    					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
	    				}
	    				// on retire la carte de la main du joueur
	    				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
	    				// on lui retire le mana en conséquence
	    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
	    			}
	    			// si le spell c'est Métamorphose
	    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Métamorphose") {
	    				iAmWaitingFor = "Métamorphose";
	    				this.tmpUUID = cardID;
	    			}
	    			// si le spell c'est Bénédiction de puissance
	    			else if(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName() == "Bénédiction de puissance") {
	    				iAmWaitingFor = "Bénédiction de puissance";
	    				this.tmpUUID = cardID;
	    			}
	    		}
	    	}
	    	else {
	    		throw new MiniHeartStoneException("Attention vous n'etes soit pas le joueur courant, soit vous n'avez pas la carte en main ou pas de mana necessaire");
	    	}
    	}
    	catch(MiniHeartStoneException e){
    		System.out.println(e.toString());
    	}
    }
    
    // selection d'un minion ou d'un hero pour spell pouvoirs héroiques etc ...
    public void select(UUID playerUUID, UUID ennemyUUID) {
    	
    	try {
	    	if (playerUUID == this.getCurrentPlayer().getPlayerID()) {
		    	if (this.iAmWaitingFor == "Métamorphose") {
		    		try {
			    		// si on cible une créature du board adverse
			    		if(this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
			    			Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
			    			// la fameuse moutonification
			    			min.setAttack(1);
			    			min.setLife(1);
			    			min.setManaCost(1);
			    			min.setName("Mouton");
			    			min.setDescription("mdr les moutons c con");
			    			min.setPictureURL("null"); // é modif quand on aura des immages si on en a un jrs
			    			min.setHasCharge(false);
			    			min.setHasVolDeVie(false);
			    			min.setHasProvocation(false);
			    			
			    			// gestion de base aprés avoir play une carte
			    			// on retire la carte de la main du joueur
							this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
							// on lui retire le mana en conséquence
							this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-4);
							
							this.iAmWaitingFor = "";
							this.tmpUUID = null;
			    			
			    		}
			    		else {
			    			throw new MiniHeartStoneException("vous etes sencé selectionner une créature du board adverse");
			    		}
		    		}
		    		catch(MiniHeartStoneException e) {
		    			System.out.println(e.toString());;
		    		}
		    	}
		    	else if (this.iAmWaitingFor == "Bénédiction de puissance" && playerUUID == this.getCurrentPlayer().getPlayerID()) {
		    		try {
		    			// si la créature est sur le board adverse
			    		if(this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
			    			Minion min = (Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
			    			min.setAttack(min.getAttack()+3);
			    			
			    			// gestion de base aprés avoir play une carte
			    			// on retire la carte de la main du joueur
							this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
							// on lui retire le mana en conséquence
							this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
							
							this.iAmWaitingFor = "";
							this.tmpUUID = null;
			    		}
			    		// si la créature est sur notre board
			    		else if(this.getCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
			    			Minion min = (Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
			    			min.setAttack(min.getAttack()+3);
			    			
			    			// gestion de base aprés avoir play une carte
			    			// on retire la carte de la main du joueur
							this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
							// on lui retire le mana en conséquence
							this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-1);
							
							this.iAmWaitingFor = "";
							this.tmpUUID = null;
			    		}
			    		else {
			    			throw new MiniHeartStoneException("Vous etes sencé selectionner une créature de votre board ou du board adverse");
			    		}
		    		}
		    		catch(MiniHeartStoneException e) {
		    			System.out.println(e.toString());
		    		}
		    	}
		    	// si on attend pour le pouvoir du mage
		    	//try {
		    	else if (this.iAmWaitingFor == "Pouvoir du mage"+this.getCurrentPlayer().getPlayerID().toString() && playerUUID == this.getCurrentPlayer().getPlayerID()) {
		    		// si tu as le mana suffisant pour jouer le pouvoir
		    		if(this.getCurrentPlayer().getHero().getMana()>=2) {
		    			// si la seclection est le joueur adverse
		    			if(this.getNotCurrentPlayer().getPlayerID() == tmpUUID) {
		    				// on applique les degats au hero adverse
		    				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(1);
		    				// on retire le prix du pouvoir héroique ici 2
		    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
		    				
		    				this.iAmWaitingFor = "";
		    				this.tmpUUID = null;
		    				HeroicPowerHasBeenUse = true;
		    			}
		    			// si c'est une créature adverse
		    			else if(this.getNotCurrentPlayer().getHero().isOnMyBoard(tmpUUID)) {
		    				this.getNotCurrentPlayer().getHero().hasBeenAttack(tmpUUID,1);
		    				// on retire le prix du pouvoir héroique ici 2
		    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
		    				
		    				this.iAmWaitingFor = "";
		    				this.tmpUUID = null;
		    				HeroicPowerHasBeenUse = true;
		    			}
		    		}
		    	}
		    	/*}
		    	catch(MiniHeartStoneException e) {
		    		System.out.print(e.toString());
		    	}*/
	    	}
	    	else {
	    		throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
	    	}
	    	iAmWaitingFor = "";
    	}
    	catch(MiniHeartStoneException e) {
    		System.out.print(e.toString());
    	}
    }
	public void power(UUID playerUUID) {
		try {
			// si le joueur est bien le joueur courant
			if(playerUUID == this.getCurrentPlayer().getPlayerID()) {
				// si tu as le mana suffisant pour jouer le pouvoir et que tu n'as pas deja use ton pouvoir 
	    		if(this.getCurrentPlayer().getHero().getMana()>=2 && HeroicPowerHasBeenUse == false) {
					// si c'est un mage 
					if (this.getCurrentPlayer().getHero().getHeroName() == "Mage") {
						iAmWaitingFor = "Pouvoir du mage"+this.getCurrentPlayer().getPlayerID().toString();
		    			//this.tmpUUID = selectCardUUID;
					}
					// si c'est un guerrier ou palladin
					else{
						this.getCurrentPlayer().getHero().power();
						this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana()-2);
						
						// le pouvoir heroic est now utilisé pour ce tour;
						HeroicPowerHasBeenUse = true;
					}
	    		}
			}
			else {
				throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
			}
		}catch(MiniHeartStoneException e) {System.out.println(e.toString());}
	}
	
	public void attack(UUID myUUID, UUID myCardUUID, UUID opponentUUID) {
		iAmWaitingFor = "";
		try {
			// si je suis bien le joueur courant
			if (myUUID == this.getCurrentPlayer().getPlayerID()) {
				// on vérifie que les deux créatures éxixtent bien
				if(this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getHero().isOnMyBoard(opponentUUID)){
					try {
						// on vérifie que ma créature n'a pas déja attaqué
						if (((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID))).getReadyToAttack() == true) {
							Minion monMin = ((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
							Minion oppMin = ((Minion)(this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(opponentUUID)));
							try {
								// si une créature adverse é provovation
								if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad() && oppMin.getHasPrococation()) {
									// on fais les degats sur les deux minions oklm
									this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
									this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
									monMin.setReadyToAttack(false);
									// si ma créature é du vol de vie
									if(monMin.getHasVolDeVie()) {
										// on augmente les pv du hero
										this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth()+monMin.getAttack());
									}
								}
								// si il n'y a pas de provoc en face
								else if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad() == false) {
									// on fais les degats sur les deux minions oklm
									this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
									this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
									monMin.setReadyToAttack(false);
									// si ma créature é du vol de vie
									if(monMin.getHasVolDeVie()) {
										// on augmente les pv du hero
										this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth()+monMin.getAttack());
									}
								}
								else {
									throw new MiniHeartStoneException("Vous devez attaquer une des créatures avec provocation");
								}
							}
							catch(MiniHeartStoneException e) {
								System.out.println(e.toString());
							}
						}
						else {
							throw new MiniHeartStoneException("Cette créature n'est pas prete à attaquer");
						}
					}
					catch(MiniHeartStoneException e) {
						System.out.println(e.toString());
					}
				}
				// si ma créature existe bien sur mon board et que j'ai sélectionné le joueur adverse
				else if (this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getPlayerID() == opponentUUID) {
					Minion monMin = ((Minion)(this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
					
					try {
						// si une créature adverse é provovation
						if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad()) {
							throw new MiniHeartStoneException("Vous devez attauqer les créatures avec provocation");
						}
					}
					catch(MiniHeartStoneException e) {
						System.out.println(e.toString());
					}
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
			else {
				throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
			}
		}catch(MiniHeartStoneException e) {System.out.println(e.toString());}
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
		aff = aff + "|||"+player1.getHero().getBoard().get(i).getName()+"	lf : " +((Minion)player1.getHero().getBoard().get(i)).getLife()+ " Att : " +((Minion)player1.getHero().getBoard().get(i)).getAttack()+"|||	";
		}
		
		aff = aff + "\n";
		aff = aff + "***********************************************************************************************************************************************\n";
		aff = aff + "\n";
		
		// affichage du board j2
		aff = aff + "Cartes en du board : \n";
		for (int i =0; i< player2.getHero().getBoard().size(); i++ ) {
		aff = aff + "|||"+player2.getHero().getBoard().get(i).getName()+"	lf : " +((Minion)player2.getHero().getBoard().get(i)).getLife()+ " Att : " +((Minion)player2.getHero().getBoard().get(i)).getAttack()+"|||	";
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
		
		// le joueur 1 play
		gm.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		//gm.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		//gm.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		gm.passTurn(yoannTchoin.getPlayerID());
		
		// le joueur 2 play
		gm.invock(pierreLaFouine.getPlayerID(), pierreLaFouine.getHero().getHand().get(0).getCardUUID());
		gm.invock(pierreLaFouine.getPlayerID(), pierreLaFouine.getHero().getHand().get(1).getCardUUID());
		gm.passTurn(pierreLaFouine.getPlayerID());
		
		
		//gm.invock(pierreLaFouine.getHero().getHand().get(2).getCardUUID(),yoannTchoin.getPlayerID());
		
		gm.attack(yoannTchoin.getPlayerID(),yoannTchoin.getHero().getBoard().get(0).getCardUUID(), pierreLaFouine.getHero().getBoard().get(0).getCardUUID());
		//gm.attack(yoannTchoin.getHero().getBoard().get(0).getCardUUID(), pierreLaFouine.getHero().getBoard().get(1).getCardUUID());
		gm.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(1).getCardUUID());
		gm.select(yoannTchoin.getPlayerID(), pierreLaFouine.getHero().getBoard().get(0).getCardUUID());
		System.out.println(gm.toString());
	}
	
}
