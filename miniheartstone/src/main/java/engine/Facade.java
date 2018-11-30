package engine;

import exception.MiniHeartStoneException;

import java.util.*;
//test commit

public class Facade {
	
	private Queue<Player> JewMatchMaking = new ArrayDeque<Player>();
	private Queue<Player> collaboratorMatchMaking = new ArrayDeque<Player>();
    private Queue<Player> onlySSMatchMaking = new ArrayDeque<Player>();

    private HashMap<UUID,Game> allCurrentGame;
	
	
	public void play(String pseudo,Hero hero,int level) {
		Player player = new Player(pseudo,hero,level);
		// envoyer son UUID au client
		
		if (level == 1) {
			if(JewMatchMaking.peek() != null) {
				Player player2 = JewMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
				// envoyer l'UUID de la game aux deux players
				
			}
			JewMatchMaking.add(player);
		}
		
		else if (level == 2) {
			if(collaboratorMatchMaking.peek() != null) {
				Player player2 = collaboratorMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
			}
			collaboratorMatchMaking.add(player);
		}
		
		else if (level == 3) {
			if(onlySSMatchMaking.peek() != null) {
				Player player2 = onlySSMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
			}
			onlySSMatchMaking.add(player);
		}
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
