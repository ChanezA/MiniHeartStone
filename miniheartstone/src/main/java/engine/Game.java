package engine;

import java.util.ArrayList;
import java.util.UUID;

import engine.util.GameListener;
import engine.util.MiniHeartStoneException;


public class Game {

    // Private attributes
    protected Player currentPlayer;
    protected Player player1;
    protected Player player2;
    protected String iAmWaitingFor = "";
    protected UUID tmpUUID = null;
    protected boolean heroicPowerUsed = false;
    protected UUID gameID;
    private ArrayList<GameListener> listeners = new ArrayList<GameListener>();
    public static final int MANA_MAX = 10;

    // Constructor
    public Game(final Player play1, final Player play2) {
        this.player1  = play1;
        this.player2 = play2;
        this.gameID = UUID.randomUUID();
        this.initGame();
    }

    // Getters
    public final Player getCurrentPlayer() {
    	return this.currentPlayer;
    }

    public Player getNotCurrentPlayer() {
        if (this.currentPlayer.equals(this.player1)) {
			return this.player2;
		} else {
			return this.player1;
		}
    }

    public final Player getPlayer1() {
    	return this.player1;
    }

    public final Player getPlayer2() {
    	return this.player2;
    }
    
    public final UUID getGameID() {
    	return this.gameID;
    }
    
    //Setters
    public void setCurrentPlayer(Player player) {
    	this.currentPlayer = player;
    }

    /**
     * Initializes the game.
     */
    private void initGame() {
        this.currentPlayer = player1;
        for (int i = 0; i < 4; i++) {
        	this.player1.getHero().draw();
        	this.player2.getHero().draw();
        }
        this.getCurrentPlayer().getHero().setMana(1);
        this.getNotCurrentPlayer().getHero().setMana(1);
        // Notify listener that the game is initializied
		for (GameListener listener : this.listeners) {
			listener.notify(this);
		}
    }

	/**
	 * Ends the turn for the player with the specified Id.
	 * @param playerID
	 * @throws MiniHeartStoneException If the specified Id is not correct
	 */
	public void endTurn(UUID playerID) throws MiniHeartStoneException {
	    	// si tu es le current player
		if (this.isCurrentPlayer(playerID)) {
		   	// echange le joueur courant avec le non courant
			this.setCurrentPlayer(this.getNotCurrentPlayer());
			heroicPowerUsed = false;
		    iAmWaitingFor = "";
		    // incrémentation du mana pour le nouveau joueur courant si besoin
		    if (this.getCurrentPlayer().getHero().getMana() < MANA_MAX) {
		     	this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() + 1);
		    }
		    // on remet toutes les créatures du joueru courant ready to attack
			/*for (AbstractCard card : this.getCurrentPlayer().getHero().getBoard()) {
				(Minion)card.set
			}*/
		    for (int i = 0; i < this.getCurrentPlayer().getHero().getBoard().size(); i++) {
		       	Minion min = (Minion) (this.getCurrentPlayer().getHero().getBoard().get(i));
		       	min.setReadyToAttack(true);
		    }
		    this.getNotCurrentPlayer().getHero().draw();
		} else {
			throw new MiniHeartStoneException("The specified player id is not correct");
		}
    }
    
    // retourne vrai si le joueur passé en param est courant ou faux pour tout le reste
    public boolean isCurrentPlayer(UUID playerUUID) {
    	boolean crt = false;
    	if (this.getCurrentPlayer().getPlayerID().equals(playerUUID)) {
    		crt = true;
    	}
    	return crt;
    }

    /**
     * Returns the board of the given player
     * @param playerUUID
     * @return The board as a List<AbstractCard>
     */
    public ArrayList<Minion> getMyBoard(UUID playerUUID) {
    	if (playerUUID.equals(this.player1.getPlayerID())) {
    		return this.getPlayer1().getHero().getBoard();
    	} else {
    		return this.getPlayer2().getHero().getBoard();
    	}
    }

    /**
     * Returns the board the opponent of the given player
     * @param playerUUID
     * @return The board as a List<AbstractCard>
     */
    public ArrayList<Minion> getOpponentBoard(UUID playerUUID){
    	if (playerUUID.equals(this.player1.getPlayerID())) {
    		return this.getPlayer2().getHero().getBoard();
    	} else {
    		return this.getPlayer1().getHero().getBoard();
    	}
    }

    public void spellsDoStuff(UUID cardID) {
    	switch (this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName()) {
			// si ce n'est pas un spell qui affecte l'ennemie ni un spell qui nécessite un ciblage
			case "Image miroir":
			case "Maitrise du blocage":
				try {
					this.getCurrentPlayer().getHero().invock(cardID);
				} catch (MiniHeartStoneException e) { e.printStackTrace(); }	
				break;
			case "Tourbillon":
				// on retire 1 pv a toutes les invocations des board des deux joueurs
				for (int i = this.getCurrentPlayer().getHero().getBoard().size() - 1; i >= 0; i--) {
					Minion min = (Minion) (this.getCurrentPlayer().getHero().getBoard().get(i));
					this.getCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
				}
				for (int i = this.getNotCurrentPlayer().getHero().getBoard().size() - 1; i >= 0; i--) {
					Minion min = (Minion) (this.getNotCurrentPlayer().getHero().getBoard().get(i));
					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
				}
				// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
				// on lui retire le mana en conséquence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 1);
				break;
			case "Consécration":
				// retire 2 pdv a tous les minions adverse
				for (int i = this.getNotCurrentPlayer().getHero().getBoard().size() - 1; i >= 0; i--) {
					Minion min = (Minion) (this.getNotCurrentPlayer().getHero().getBoard().get(i));
					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 2);
				}
				// degats sur le joueur adverse
				this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(2);
				// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
				// on lui retire le mana en conséquence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 4);
				break;
			case "Explosion des arcanes":
				// retire 1 pdv a tous les minions adverse
				for (int i = this.getNotCurrentPlayer().getHero().getBoard().size() - 1; i >= 0; i--) {
					Minion min = (Minion) (this.getNotCurrentPlayer().getHero().getBoard().get(i));
					this.getNotCurrentPlayer().getHero().hasBeenAttack(min.getCardUUID(), 1);
				}
				// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID));
				// on lui retire le mana en conséquence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 2);
				break;
			case "Métamorphose":
			case "Bénédiction de puissance":
				iAmWaitingFor = this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName();
				this.tmpUUID = cardID;
				break;
			default:
				throw new IllegalArgumentException("Ce spell n'existe pas" + this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getName());
		}
	}

    public void invock(UUID playerUUID, UUID cardID) {
    	try {
    		iAmWaitingFor = "";
	    	// si tu es bien le joueur courant et que la carte est bien dans ta main et que tu as bien le mana necessaire
	    	if (this.isCurrentPlayer(playerUUID)
	    		&& this.getCurrentPlayer().getHero().isOnMyHand(cardID)
	    			&& this.getCurrentPlayer().getHero().getMana() >= this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID).getManaCost()) {
	    		
	    		// si cette carte est un minion
	    		if (this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Minion) {
					this.getCurrentPlayer().getHero().invock(cardID);
					// si c'est un spell
				} else if (this.getCurrentPlayer().getHero().getCardFromHandByUUID(cardID) instanceof Spell) {
	    			spellsDoStuff(cardID);
	    		}
	    	} else {
	    		throw new MiniHeartStoneException("Attention vous n'etes soit pas le joueur courant, soit vous n'avez pas la carte en main ou pas de mana necessaire");
	    	}
    	} catch (MiniHeartStoneException e) {
    		System.out.println(e.toString());
    	}
    }

    public void metamorphoseDoStuff(UUID playerUUID, UUID ennemyUUID) {
    	Minion min = (Minion) (this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
    	// la fameuse moutonification
		min.setAttack(1);
		min.setLife(1);
		min.setManaCost(1);
		min.setName("Mouton");
		min.setDescription("mdr les moutons c con");
		min.setPictureURL("null"); // é modif quand on aura des immages si on en a un jrs
		min.setHasCharge(false);
		min.setHasLifeSteal(false);
		min.setHasTaunt(false);
		// gestion de base aprés avoir play une carte
		// on retire la carte de la main du joueur
		this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
		// on lui retire le mana en conséquence
		this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 4);
		this.iAmWaitingFor = "";
		this.tmpUUID = null;
	}

	public void benedictionDoStuff(UUID playerUUID, UUID ennemyUUID){
		try {
			// si la créature est sur le board adverse
			if (this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
				Minion min = (Minion) (this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
				min.setAttack(min.getAttack() + 3);
				// gestion de base aprés avoir play une carte
				// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
				// on lui retire le mana en conséquence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 1);
				this.iAmWaitingFor = "";
				this.tmpUUID = null;
				// si la créature est sur notre board
			} else if (this.getCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
				Minion min = (Minion) (this.getCurrentPlayer().getHero().getCardFromBoardByUUID(ennemyUUID));
				min.setAttack(min.getAttack() + 3);

				// gestion de base aprés avoir play une carte
				// on retire la carte de la main du joueur
				this.getCurrentPlayer().getHero().getHand().remove(this.getCurrentPlayer().getHero().getCardFromHandByUUID(tmpUUID));
				// on lui retire le mana en conséquence
				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 1);
				this.iAmWaitingFor = "";
				this.tmpUUID = null;
			} else {
				throw new MiniHeartStoneException("Vous etes sencé selectionner une créature de votre board ou du board adverse");
			}
		} catch (MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
	}

    // selection d'un minion ou d'un hero pour spell pouvoirs héroiques etc ...
    public void select(UUID playerUUID, UUID ennemyUUID) {
    	
    	try {
	    	if (playerUUID.equals(this.getCurrentPlayer().getPlayerID())) {
		    	if (this.iAmWaitingFor.equals("Métamorphose")) {
		    		try {
			    		// si on cible une créature du board adverse
			    		if (this.getNotCurrentPlayer().getHero().isOnMyBoard(ennemyUUID)) {
			    			metamorphoseDoStuff(playerUUID, ennemyUUID);
			    		} else {
			    			throw new MiniHeartStoneException("vous etes sensé selectionner une créature du board adverse");
			    		}
		    		} catch(MiniHeartStoneException e) {
		    			System.out.println(e.toString());
		    		}
		    	} else if (this.iAmWaitingFor.equals("Bénédiction de puissance") && playerUUID.equals(this.getCurrentPlayer().getPlayerID())) {
					benedictionDoStuff(playerUUID, ennemyUUID);
					// si on attend pour le pouvoir du mage
					//try {
				} else if (this.iAmWaitingFor.equals("Pouvoir du mage" + this.getCurrentPlayer().getPlayerID().toString()) && playerUUID.equals(this.getCurrentPlayer().getPlayerID())) {
		    		// si tu as le mana suffisant pour jouer le pouvoir
		    		if (this.getCurrentPlayer().getHero().getMana() >= 2) {
						// si la seclection est le joueur adverse
						if (this.getNotCurrentPlayer().getPlayerID().equals(tmpUUID)) {
							// on applique les degats au hero adverse
							this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(1);
							// on retire le prix du pouvoir héroique ici 2
							this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 2);
							this.iAmWaitingFor = "";
							this.tmpUUID = null;
							heroicPowerUsed = true;
						// si c'est une créature adverse
						} else if (this.getNotCurrentPlayer().getHero().isOnMyBoard(tmpUUID)) {
		    				this.getNotCurrentPlayer().getHero().hasBeenAttack(tmpUUID,1);
		    				// on retire le prix du pouvoir héroique ici 2
		    				this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 2);
		    				this.iAmWaitingFor = "";
		    				this.tmpUUID = null;
		    				heroicPowerUsed = true;
		    			}
		    		}
		    	}
		    	/*}
		    	catch(MiniHeartStoneException e) {
		    		System.out.print(e.toString());
		    	}*/
	    	} else {
	    		throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
	    	}
	    	iAmWaitingFor = "";
    	} catch (MiniHeartStoneException e) {
    		System.out.print(e.toString());
    	}
    }

	public void power(UUID playerUUID) {
		try {
			// si le joueur est bien le joueur courant
			if (playerUUID.equals(this.getCurrentPlayer().getPlayerID())) {
				// si tu as le mana suffisant pour jouer le pouvoir et que tu n'as pas deja use ton pouvoir
	    		if (this.getCurrentPlayer().getHero().getMana() >= 2 && !heroicPowerUsed) {
					// si c'est un mage 
					if (this.getCurrentPlayer().getHero().getHeroName().equals("Mage")) {
						iAmWaitingFor = "Pouvoir du mage" + this.getCurrentPlayer().getPlayerID().toString();
						//this.tmpUUID = selectCardUUID;
						// si c'est un guerrier ou paladin
					} else{
						this.getCurrentPlayer().getHero().power();
						this.getCurrentPlayer().getHero().setMana(this.getCurrentPlayer().getHero().getMana() - 2);
						// le pouvoir heroic est now utilisé pour ce tour;
						heroicPowerUsed = true;
					}
	    		}
			} else {
				throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
			}
		} catch (MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
	}
	
	public void attack(UUID myUUID, UUID myCardUUID, UUID opponentUUID) {
		iAmWaitingFor = "";
		try {
			// si je suis bien le joueur courant
			if (myUUID.equals(this.getCurrentPlayer().getPlayerID())) {
				// on vérifie que les deux créatures éxixtent bien
				if (this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getHero().isOnMyBoard(opponentUUID)) {
					try {
						// on vérifie que ma créature n'a pas déja attaqué
						if (((Minion) (this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID))).isReadyToAttack()) {
							Minion monMin = ((Minion) (this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
							Minion oppMin = ((Minion) (this.getNotCurrentPlayer().getHero().getCardFromBoardByUUID(opponentUUID)));
							try {
								// si une créature adverse é provovation
								if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad() && oppMin.getHasTaunt()) {
									// on fais les degats sur les deux minions oklm
									this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
									this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
									monMin.setReadyToAttack(false);
									// si ma créature é du vol de vie
									if (monMin.getHasLifeSteal()) {
										// on augmente les pv du hero
										this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth() + monMin.getAttack());
									}
									// si il n'y a pas de provoc en face
								} else if (!this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad()) {
									// on fais les degats sur les deux minions oklm
									this.getNotCurrentPlayer().getHero().hasBeenAttack(opponentUUID, monMin.getAttack());
									this.getCurrentPlayer().getHero().hasBeenAttack(myCardUUID, oppMin.getAttack());
									monMin.setReadyToAttack(false);
									// si ma créature é du vol de vie
									if (monMin.getHasLifeSteal()) {
										// on augmente les pv du hero
										this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth() + monMin.getAttack());
									}
								} else {
									throw new MiniHeartStoneException("Vous devez attaquer une des créatures avec provocation");
								}
							} catch (MiniHeartStoneException e) {
								System.out.println(e.toString());
							}
						} else {
							throw new MiniHeartStoneException("Cette créature n'est pas prete à attaquer");
						}
					} catch (MiniHeartStoneException e) {
						System.out.println(e.toString());
					}
				// si ma créature existe bien sur mon board et que j'ai sélectionné le joueur adverse
				} else if (this.getCurrentPlayer().getHero().isOnMyBoard(myCardUUID) && this.getNotCurrentPlayer().getPlayerID().equals(opponentUUID)) {
					Minion monMin = ((Minion) (this.getCurrentPlayer().getHero().getCardFromBoardByUUID(myCardUUID)));
					
					try {
						// si une créature adverse é provovation
						if (this.getNotCurrentPlayer().getHero().aCardWithProvocationInMyBorad()) {
							throw new MiniHeartStoneException("Vous devez attauqer les créatures avec provocation");
						}
					} catch(MiniHeartStoneException e) {
						System.out.println(e.toString());
					}
					// on fais les degats sur l'adversaire
					this.getNotCurrentPlayer().getHero().myHeroHasBeenAttack(monMin.getAttack());
					monMin.setReadyToAttack(false);
					// si ma créature du vol de vie
					if (monMin.getHasLifeSteal()) {
						// on augmente les pv du hero
						this.getCurrentPlayer().getHero().setHealth(this.getCurrentPlayer().getHero().getHealth() + monMin.getAttack());
					}
				}
			} else {
				throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
			}
		} catch (MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
	}
	
	
	
	public String toString() {
		String aff = "					" + this.getPlayer1().getName() + "	" + this.getPlayer1().getHero().getHeroName() + "\n";
		aff = aff + "					-----------------------------					\n";
		aff = aff + "					|  PV : " + this.getPlayer1().getHero().getHealth()+"  AR : " + this.getPlayer1().getHero().getArmor() + "  MN : " + this.getPlayer1().getHero().getMana() + "  |					\n"
			+ "					-----------------------------					\n";
	
		aff = aff + "Cartes en main : \n";
		
		// affichage de la main j1
		for (int i = 0; i < player1.getHero().getHand().size(); i++ ) {
		aff = aff + "|||" + player1.getHero().getHand().get(i).getName() + " Mana Cost : " + player1.getHero().getHand().get(i).getManaCost() + "|||	";
		}
		aff = aff + "\n";
		
		// affichage du board j1
		aff = aff + "Cartes en du board : \n";
		for (int i = 0; i < player1.getHero().getBoard().size(); i++ ) {
		aff = aff + "|||" + player1.getHero().getBoard().get(i).getName() + "	lf : " +((Minion) player1.getHero().getBoard().get(i)).getLife() + " Att : " + ((Minion) player1.getHero().getBoard().get(i)).getAttack() + "|||	";
		}
		
		aff = aff + "\n";
		aff = aff + "***********************************************************************************************************************************************\n";
		aff = aff + "\n";
		
		// affichage du board j2
		aff = aff + "Cartes en du board : \n";
		for (int i = 0; i < player2.getHero().getBoard().size(); i++ ) {
		aff = aff + "|||" + player2.getHero().getBoard().get(i).getName() + "	lf : " + ((Minion)player2.getHero().getBoard().get(i)).getLife() + " Att : " + ((Minion) player2.getHero().getBoard().get(i)).getAttack() + "|||	";
		}
		aff = aff + "\n";
		
		// affichage de la main j2
		aff = aff + "Cartes en main : \n";
		for (int i = 0; i < player2.getHero().getHand().size(); i++ ) {
		aff = aff + "|||" + player2.getHero().getHand().get(i).getName() + " Mana Cost : " + player2.getHero().getHand().get(i).getManaCost() + "|||	";
		}
		aff = aff + "\n";
		
		aff = aff + "					-----------------------------					\n";
		aff = aff + "					|  PV : " + this.getPlayer2().getHero().getHealth() + "  AR : " + this.getPlayer2().getHero().getArmor() + "  MN : " + this.getPlayer2().getHero().getMana() + "  |					\n"
			+ "					-----------------------------					\n";
		aff = aff + "					" + this.getPlayer2().getName() + "	" + this.getPlayer2().getHero().getHeroName() + "\n";
		
		
		return aff;
	}

	public boolean isEnded() {
    	return this.player2.getHero().isItDead() || this.player1.getHero().isDead;
	}

	public void addGameListener(GameListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(GameListener listener) {
    	this.listeners.remove(listener);
	}

	public void ntyGameIsReady() {
		for (GameListener gl : this.listeners) {
			gl.notify(this);
		}
	}

	public static void main(String[] args) {

		AbstractHero yoann = new Magus();
		AbstractHero pierre = new Paladin();
		Player yoannTchoin = new Player("suce teub", yoann, 3);
		Player pierreLaFouine = new Player("l'appel d'air", pierre, 4);
		Game game = new Game(yoannTchoin, pierreLaFouine);
		// le joueur 1 play
		game.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		//game.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		//game.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(0).getCardUUID());
		try {
			game.endTurn(yoannTchoin.getPlayerID());
		} catch (MiniHeartStoneException e) {
			e.printStackTrace();
		}
		// le joueur 2 play
		game.invock(pierreLaFouine.getPlayerID(), pierreLaFouine.getHero().getHand().get(0).getCardUUID());
		game.invock(pierreLaFouine.getPlayerID(), pierreLaFouine.getHero().getHand().get(1).getCardUUID());
		try {
			game.endTurn(pierreLaFouine.getPlayerID());
		} catch (MiniHeartStoneException e) {
			e.printStackTrace();
		}
		//game.invock(pierreLaFouine.getHero().getHand().get(2).getCardUUID(),yoannTchoin.getPlayerID());
		game.attack(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getBoard().get(0).getCardUUID(), pierreLaFouine.getHero().getBoard().get(0).getCardUUID());
		//game.attack(yoannTchoin.getHero().getBoard().get(0).getCardUUID(), pierreLaFouine.getHero().getBoard().get(1).getCardUUID());
		game.invock(yoannTchoin.getPlayerID(), yoannTchoin.getHero().getHand().get(1).getCardUUID());
		game.select(yoannTchoin.getPlayerID(), pierreLaFouine.getHero().getBoard().get(0).getCardUUID());
		System.out.println(game.toString());
	}
}
