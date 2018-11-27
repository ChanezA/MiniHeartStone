package com.miniheartstone.engine;

import java.util.*;
import java.util.HashMap;


public class Facade {
	
	protected Queue<Player> JewMatchMaking;
	protected Queue<Player> collaboratorMatchMaking;
	protected Queue<Player> onlySSMatchMaking;
	
	protected HashMap<UUID,Game> allCurrentGame;
	
	
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
			if(collaboratorMatchMaking.peek() != null) {
				Player player2 = collaboratorMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
			}
			collaboratorMatchMaking.add(player);
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
		return // a finir
	}*/
}
