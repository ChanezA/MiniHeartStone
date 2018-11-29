package engine;

import exception.MiniHeartStoneException;

import java.util.*;
//test commit

public class Facade {
	
	private Queue<Player> JewMatchMaking;
	private Queue<Player> collaboratorMatchMaking;
    private Queue<Player> onlySSMatchMaking;

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
		try {
		
			Game game = allCurrentGame.get(gameID);
			if (game.getCurrentPlayer().getPlayerID() == playerID) {
				
				Card ourcard=game.getCurrentPlayer().getSpecificCard(cardID);
				//ecrire une fonction ajoute carte dans game pour ajouter une carte et appliquer ses effets si elle en as au d'ajouter seulement
				game.getBoard(game.getCurrentPlayer()).add(ourcard);
				game.getCurrentPlayer().removeCardHand(ourcard);
				ourcard.setIsInvock(true);
				
			}

			else {
                throw new MiniHeartStoneException("Vous n'etes pas le joueur courant");
            }
			
		} catch (IllegalAccessException e) {

		    System.out.println("la carte n'est pas dans la main");

		  }
		  catch (MiniHeartStoneException e) {
              System.out.println("Vous n'etes pas le joueur courant");
          }
		
		
	}

	public void endTurn(UUID gameID,UUID playerID, UUID cardID) {
	    try {
            Game game = allCurrentGame.get(gameID);
            if (game.getCurrentPlayer().getPlayerID() == playerID) {
                game.changedCurrentPlayer();
            }
            else{
                throw new MiniHeartStoneException("tu peux pas passer si c'est pas à toi de jouer ;)");
            }
        }
        catch (MiniHeartStoneException e){
            System.out.println("tu peux pas passer si c'est pas à toi de jouer :O");
        }
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
        if(game.getCurrentPlayer().getPlayerID() == playerID){
            try {
                Card myCard = game.getCurrentPlayer().getSpecificCard(cardID);
                Card oppCard = game.getNotCurrentPlayer().getSpecificCard(cardOpponentID);
                if (myCard instanceof Minion && myCard.getIsInvock() && game.getBoard(game.getCurrentPlayer()).contains(myCard) && oppCard instanceof Minion && oppCard.getIsInvock() && game.getOpponentBoard(game.getCurrentPlayer()).contains(oppCard)){
                    Minion myMinion = (Minion) myCard;
                    Minion oppMinion = (Minion) oppCard;
                    myMinion.setLife(myMinion.getLife()-oppMinion.getAttack());
                    if(myMinion.getLife()<=0) { game.getBoard(game.getCurrentPlayer()).remove(myCard);}

                    oppMinion.setLife(oppMinion.getLife()-myMinion.getAttack());
                    if(oppMinion.getLife()<=0) { game.getOpponentBoard(game.getCurrentPlayer()).remove(oppCard);}
                }
            }
            catch (IllegalAccessException e) {
                System.out.println("la carte n'est pas dans la main");
            }

        }


    }

}
