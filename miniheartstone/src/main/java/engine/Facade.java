package engine;

import exception.MiniHeartStoneException;

import java.util.*;
//test commit

public class Facade {
	
	private MatchMaking MM;

    private HashMap<UUID,Game> allCurrentGame = new HashMap<UUID,Game>();
	
	
	public void play(String pseudo,Hero hero,int level) {
		MatchMaking(pseudo,hero,level,allCurrentGame);
	}
	
	public List<Card> getPioche(UUID gameID,UUID playerID){
		
		Game game = allCurrentGame.get(gameID);
		if (game.getPlayer1().getPlayerID() == playerID) {
			return game.getPlayer1().getHand();
		}
		
		else {
			return game.getPlayer2().getHand();
		}
	}
	
	
	/*public List<Hero> getHero() {
	 * creer une list et la remplir en récuperant les heros de la base de données 
	}*/
	//la methode met une card sur le plateau
	public void invoke (UUID gameID,UUID playerID, UUID cardID) throws MiniHeartStoneException {
		Game game = allCurrentGame.get(gameID);
		game.invock(playerID, cardID);
	}

	public void endTurn(UUID gameID,UUID playerID) {
		Game game = allCurrentGame.get(gameID);
		game.endTurn(playerID);
    }

    //à faire quand on aura finit spring
    /*public List<Hero> getHeros(){
	    return
    }*/

    public void power(){
        // à implémenter je sais pas quand
    }

    public void attack(UUID gameID,UUID playerID, UUID cardID,UUID cardOpponentID){
        Game game = allCurrentGame.get(gameID);
        game.attack(playerID, cardID, cardOpponentID);
    }

}
