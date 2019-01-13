package engine;

import java.util.*;

import engine.util.GameListener;
import engine.util.MiniHeartStoneException;

public class EngineInterface {
	public static HashMap<UUID,Game> allCurrentGame = new HashMap<UUID,Game>();

	public static Queue<Player> mmNoob = new ArrayDeque<>();
	public static Queue<Player> mmMid = new ArrayDeque<>();
	public static Queue<Player> mmPro = new ArrayDeque<>();

	// ici faire du spring pour recup les noms les héros jouables
	public static ArrayList<String> getAllHeroDescription() {

		ArrayList<String> rep = new ArrayList<String>();
		rep.add("Mage");
		rep.add("Guerrier");
		rep.add("Paladin");
		return rep;
	}

	public static AbstractHero returnHero(String heroStr) {
		try {
			if (heroStr.equals("Mage")) {
				return new Magus();
			}
			else if (heroStr.equals("Guerrier")) {
				return new Warrior();
			}
			else if (heroStr.equals("Paladin")) {
				return new Paladin();
			}
			else {
				throw new MiniHeartStoneException("Vous devez Choisir Mage, Guerrier ou Paladin");
			}
		}
		catch(MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public static UUID wantPlay(int lvl, String pseudo, String heroStr, GameListener gl) {

		// création du player
		// lvl 1 = noob 2 = mid 3 = pro
		Player play = new Player(pseudo,returnHero(heroStr),lvl);

		try {
			if (lvl == 1) {
				if (!mmNoob.isEmpty()) {
					// création de la game
					Game game = new Game(mmNoob.poll(),play);
					game.addGameListener(gl);
					//game.ntyGameIsReady();
					allCurrentGame.put(game.getGameID(),game);
				}
				else {
					mmNoob.offer(play);
				}
			}
			else if (lvl == 2) {
				if (!mmMid.isEmpty()) {
					// création de la game
					Game game = new Game(mmMid.poll(),play);
					game.addGameListener(gl);
					allCurrentGame.put(game.getGameID(),game);
				}
				else {
					mmMid.offer(play);
				}
			}
			else if (lvl == 3) {
				if (!mmPro.isEmpty()) {
					// création de la game
					Game game = new Game(mmPro.poll(),play);
					game.addGameListener(gl);
					allCurrentGame.put(game.getGameID(),game);
				}
				else {
					mmPro.offer(play);
				}
			}
			else {
				throw new MiniHeartStoneException("Vous devez selectionner un de ces 3 niveaux : 1 pour noob, 2 pour mid, 3 pour pro");
			}
			return play.getPlayerID();
		}
		catch(MiniHeartStoneException e) {
			System.out.println(e.toString());
		}

		return play.getPlayerID();
	}

	// retourne les cartes de ma main
	public static String[] getMyHand(UUID gameUUID, UUID playerUUID) {
		Game game = allCurrentGame.get(gameUUID);
		ArrayList<AbstractCard> hand;
		System.out.println("1 : " + playerUUID);
		System.out.println("2 : " + game);
		if(game.getPlayer1().getPlayerID().equals(playerUUID)) {
			hand = game.getPlayer1().getHero().getHand();
		}
		else {
			hand = game.getPlayer1().getHero().getHand();
		}

		String[] ret = new String[hand.size()];
		int i = 0;
		String str;
		for (AbstractCard card : hand) {
			str = card.getCardUUID().toString();
			str += "!!" + card.getName() + "!!" + card.getDescription() + "!!" + card.getManaCost();
			if (card instanceof Minion) {
				str += "!!" + ((Minion) card).getAttack() + "!!" + ((Minion) card).getLife();
			}
			ret[i] = str;
			i++;
		}
		return ret;
	}

	// retourne les minions de mon board
	public static String[] getMyBoard(UUID gameUUID, UUID playerUUID) {
		Game game = allCurrentGame.get(gameUUID);
		ArrayList<Minion> board;
		if(game.getPlayer1().getPlayerID().equals(playerUUID)) {
			board = game.getPlayer1().getHero().getBoard();
		}
		else {
			board = game.getPlayer1().getHero().getBoard();
		}

		String[] ret = new String[board.size()];
		int i = 0;
		String str;
		for (Minion card : board) {
			str = card.getCardUUID().toString();
			str += "!!" + card.getName() + "!!" + card.getDescription() + "!!" + card.getManaCost();
			str += "!!" + card.getAttack() + "!!" + card.getLife();
			ret[i] = str;
			i++;
		}
		return ret;
	}

	// retourne les minions du board adverse
	public static String[] getOpponantBoard(UUID gameUUID, UUID playerUUID) {
		Game game = allCurrentGame.get(gameUUID);
		if(game.getPlayer1().getPlayerID().equals(playerUUID)) {
			return EngineInterface.getMyBoard(gameUUID,game.getPlayer2().getPlayerID());
		}
		else {
			return EngineInterface.getMyBoard(gameUUID,game.getPlayer1().getPlayerID());
		}
	}


	// xd
	public static void invock(UUID gamUUID, UUID playerUUID, UUID cardID) {
		allCurrentGame.get(gamUUID).invock(playerUUID, cardID);
	}

	//lol
	public static void attack(UUID gamUUID, UUID playerUUID, UUID myCardUUID, UUID oppCardUUID) {
		allCurrentGame.get(gamUUID).attack(playerUUID, myCardUUID, oppCardUUID);
	}

	//wtf
	public static void select(UUID gamUUID, UUID playerUUID, UUID cardUUID) {
		allCurrentGame.get(gamUUID).select(playerUUID, cardUUID);
	}

	//power
	public static void power(UUID gamUUID, UUID playerUUID) {
		allCurrentGame.get(gamUUID).power(playerUUID);
	}

	// culte
	public static void passTurn(UUID gamUUID, UUID playerUUID) {
		try {
			allCurrentGame.get(gamUUID).endTurn(playerUUID);
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
	}
}