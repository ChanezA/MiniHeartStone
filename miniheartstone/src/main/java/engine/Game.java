package engine;

import exception.MiniHeartStoneException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Game {

    // Private attributes
    private Player currentPlayer;

    private Player player1;
    private Player player2;

    private List<Card> boardP1;
    private List<Card> boardP2;
    
    private UUID gameID;

    private int round;

    // Constructor
    public Game(Player player1, Player player2) {
        this.player1  = player1;
        this.player1.setGame(this);
        this.player2 = player2;
        this.player2.setGame(this);
        this.boardP1 = new ArrayList<Card>();
        this.boardP2 = new ArrayList<Card>();
        this.round = 0;
        
        UUID gameID = UUID.randomUUID();
        
        this.initGame();
    }

    // Getters
    public Player getCurrentPlayer() { return this.currentPlayer; }
    public Player getNotCurrentPlayer() {
        if (this.currentPlayer == this.player1) return this.player2;
        else return this.player1;
    }
    public Player getPlayer1() { return this.player1; }
    public Player getPlayer2() { return this.player2; }
    public List<Card> getBoardP1() { return this.boardP1; }
    public List<Card> getBoardP2() { return this.boardP2; }
    public int getRound() { return this.round; }
    public UUID getGameID () {return this.gameID;}

    /**
     * Initializes the game
     */
    private void initGame() {
        this.currentPlayer = player1;
        int i;
        Hero hero = player1.getHero();
        for (i = 0; i < 4; i++) {
            player1.addCardToHand(this.draw(hero));
        }
        hero = player2.getHero();
        for (i = 0; i < 4; i++) {
            player2.addCardToHand(this.draw(hero));
        }
    }

    /**
     * Does all the action of a round beginning
     */
    private void initRound() {
        Player currP = this.currentPlayer;
        Hero hero = currP.getHero();

        if (this.round < Hero.MANA_MAX) hero.setMana(this.round);
        else hero.setMana(Hero.MANA_MAX);

        Game.draw(hero);
    }

    /**
     * Returns one of the card of the given hero deck
     */
    private static Card draw(Hero hero) {
        int size = hero.getDeck().size();
        int i = (int)(Math.random()*size);
        return hero.getDeck().get(i);
    }

    /**
     * Returns the board of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public List<Card> getBoard(Player player){
    	if (player == this.player1 ) {
    		return this.boardP1;
    	} else {
    		return this.boardP2;
    	}
    }

    /**
     * Returns the board the opponent of the given player
     * @param player
     * @return The board as a List<Card>
     */
    public List<Card> getOpponentBoard(Player player){
    	if (player == this.player1 ) {
    		return this.boardP2;
    	} else {
    		return this.boardP1;
    	}
    }
    
    public Player getOpponentPlayer(Player player){
    	if (player == this.player1 ) {
    		return this.player1;
    	} else {
    		return this.player2;
    	}
    }

    public void changedCurrentPlayer() {
        if (this.currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    public Card targetOpponentCard() {
        while(true) {
            //REVOIR CETTE METHODE MDR
            return(getBoard(getOpponentPlayer(this.getCurrentPlayer())).get(0));
        }
    }

    /**
     *  Permet de déduire le mana et d'activer les effets. Attention, revoir targetOpponentCard
     */
    public void putOnBoard(Card card, Player player) {
        player.getHero().setMana(player.getHero().getMana() - card.getManaCost());
        card.getEffect().putOnBoardEffect(this);
        if (card.getEffect().hasTarget) {
            card.getEffect().putOnBoardEffect(this, targetOpponentCard());
        }
    }

    public void invock(UUID playerID, UUID cardID)throws MiniHeartStoneException {
        try {
            if (this.getCurrentPlayer().getPlayerID() == playerID) {

                Card ourcard = this.getCurrentPlayer().getSpecificCard(cardID);
                putOnBoard(ourcard, this.getCurrentPlayer());
                if (ourcard instanceof Minion) {
                    this.getBoard(this.getCurrentPlayer()).add(ourcard);
                }
                this.getCurrentPlayer().removeCardHand(ourcard);
                ourcard.setIsInvock(true);
            } else {
                throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
            }
        }
        catch (IllegalAccessException e) {
            System.out.println("la carte n'est pas dans la main");
        }
            catch (MiniHeartStoneException e) {
            System.out.println(e.getMessage());
        }
    }

    public void attack(UUID playerID, UUID cardID,UUID cardOpponentID){
        if(this.getCurrentPlayer().getPlayerID() == playerID) {
            try {
                Card myCard = this.getCurrentPlayer().getSpecificCard(cardID);
                Card oppCard = this.getNotCurrentPlayer().getSpecificCard(cardOpponentID);
                if (myCard instanceof Minion && myCard.getIsInvock() && this.getBoard(this.getCurrentPlayer()).contains(myCard) && oppCard instanceof Minion && oppCard.getIsInvock() && this.getOpponentBoard(this.getCurrentPlayer()).contains(oppCard)) {
                    Minion myMinion = (Minion) myCard;
                    Minion oppMinion = (Minion) oppCard;
                    myMinion.setLife(myMinion.getLife() - oppMinion.getAttack());
                    if (myMinion.getLife() <= 0) {
                        this.getBoard(this.getCurrentPlayer()).remove(myCard);
                    }

                    oppMinion.setLife(oppMinion.getLife() - myMinion.getAttack());
                    if (oppMinion.getLife() <= 0) {
                        this.getOpponentBoard(this.getCurrentPlayer()).remove(oppCard);
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println("la carte n'est pas dans la main");
            }
        }
    }

    public void attack(UUID playerID, UUID cardID, Hero hero){
        if(this.getCurrentPlayer().getPlayerID() == playerID) {
            try {
                Card myCard = this.getCurrentPlayer().getSpecificCard(cardID);
                if (myCard instanceof Minion && myCard.getIsInvock() && this.getBoard(this.getCurrentPlayer()).contains(myCard)) {
                    Minion myMinion = (Minion) myCard;
                    //On vérifie que le hero passé en paramètre soit le même, normalement c'est le cas
                    assert getNotCurrentPlayer().getHero().isEqual(hero);
                    int degats = myMinion.getAttack();
                    while(getNotCurrentPlayer().getHero().getArmor() > 0 && degats > 0) {
                        getNotCurrentPlayer().getHero().setArmor(getNotCurrentPlayer().getHero().getArmor()-1);
                        degats --;
                    }
                    getNotCurrentPlayer().getHero().setHealth(getNotCurrentPlayer().getHero().getHealth() - degats);
                    if (getNotCurrentPlayer().getHero().getHealth() <= 0) {
                        System.out.println(getCurrentPlayer().getName() + "a gagné");
                        //A remplacer par une méthode aGagné(Player playerWon, Player playerLost)
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println("la carte n'est pas dans la main");
            }
        }
    }

    public void endTurn(UUID playerID){
        try {
            if (this.getCurrentPlayer().getPlayerID() == playerID) {
                this.changedCurrentPlayer();
                this.getCurrentPlayer().getHand().add(Game.draw(this.getCurrentPlayer().getHero()));
            }
            else{
                throw new MiniHeartStoneException("tu peux pas passer si c'est pas à toi de jouer ;)");
            }
        }
        catch (MiniHeartStoneException e){
            System.out.println("tu peux pas passer si c'est pas à toi de jouer :O");
        }
    }
}
