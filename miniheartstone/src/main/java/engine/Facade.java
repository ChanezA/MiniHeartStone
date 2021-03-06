package engine;

import java.util.*;
//test commit

public class Facade {
	
	protected Queue<Player> jewMatchMaking;
	protected Queue<Player> collabMatchMaking;
	protected Queue<Player> onlySSMatchMaking;
	
	protected HashMap<UUID,Game> allCurrentGame;
	
	
	public void play(String pseudo, AbstractHero hero, int level) {
		Player player = new Player(pseudo,hero,level);
		// envoyer son UUID au client
		
		if (level == 1) {
			if(jewMatchMaking.peek() != null) {
				Player player2 = jewMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
				// envoyer l'UUID de la game aux deux players
				
			}
			jewMatchMaking.add(player);
		}
		
		else if (level == 2) {
			if(collabMatchMaking.peek() != null) {
				Player player2 = collabMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
			}
			collabMatchMaking.add(player);
		}
		
		else if (level == 3) {
			if(collabMatchMaking.peek() != null) {
				Player player2 = collabMatchMaking.poll();
				Game game = new Game(player,player2);
				allCurrentGame.put(game.getGameID(),game);
			}
			collabMatchMaking.add(player);
		}
	}
	
	public List<AbstractCard> getPioche(UUID gameID, UUID playerID){
		
		Game game = allCurrentGame.get(gameID);
		if (game.getPlayer1().getPlayerID() == playerID) {
			return game.getPlayer1().getHero().getHand();
		}
		
		else {
			return game.getPlayer2().getHero().getHand();
		}
	}
	
	
	/*public List<AbstractHero> getHero() {
	 * creer une list et la remplir en récuperant les heros de la base de données 
	}*/
	//la methode met une card sur le plateau
	public void invoke (UUID gameID,UUID playerID, UUID cardID) {
		try {
		
			Game game = allCurrentGame.get(gameID);
			if (game.getCurrentPlayer().getPlayerID() == playerID) {
				
				//AbstractCard ourcard = game.getCurrentPlayer().getSpecificCard(cardID);
				//ecrire une fonction ajoute carte dans game pour ajouter une carte et appliquer ses effets si elle en as au d'ajouter seulement
				game.getCurrentPlayer().getHero().getBoard();
				//game.getCurrentPlayer().removeCardHand(ourcard);
				
			}
			
		} catch (Exception e) {

		    System.out.println("la carte n'est pas dans la main");

		  }
		
		
	}
}
