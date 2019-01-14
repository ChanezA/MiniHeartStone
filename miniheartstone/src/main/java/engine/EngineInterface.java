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
					System.out.println("******AAAAAAAAAAAAAAAA");
					// création de la game
					Game game = new Game(mmNoob.poll(),play);
					game.addGameListener(gl);
					game.ntyGameIsReady();
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
		System.out.println("---> j'ai trouvé la game d'ID : " + game.getGameID());
		ArrayList<AbstractCard> hand;

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
			/*
			if (card instanceof Minion) {
				str += "!!" + ((Minion) card).getAttack() + "!!" + ((Minion) card).getLife();
			}
			*/
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
			board = game.getPlayer2().getHero().getBoard();
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
		ArrayList<Minion> board;
		if(game.getPlayer1().getPlayerID().equals(playerUUID)) {
			board = game.getPlayer2().getHero().getBoard();
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
	public static boolean passTurn(UUID gamUUID, UUID playerUUID) {
		try {
			boolean succes = false;
			if(allCurrentGame.get(gamUUID).getCurrentPlayer().getPlayerID().equals(playerUUID)) { succes = true; System.out.println("oui bordel de merde");}
			System.out.println("UUID de la gamen par jrs : "+gamUUID);
			System.out.println("UUID de la game: "+allCurrentGame.get(gamUUID).getGameID());

			System.out.println("UUID du jrs couranr par jrs : "+playerUUID);
			System.out.println("UUID du jrs courant : "+allCurrentGame.get(gamUUID).getCurrentPlayer().getPlayerID());
			(allCurrentGame.get(gamUUID)).endTurn(playerUUID);
			return succes;
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
		return false;
	}

	/*
	 * retourne une game grace a sont UUID
	 */
	public static Game getGameFromUUID(UUID gamUUID) {
		return allCurrentGame.get(gamUUID);
	}

	public static void main(String[] args) {
		AbstractHero yoann = new Magus();
		AbstractHero pierre = new Paladin();

		Player oui = new Player("oui",yoann,3);
		Player non = new Player("non",pierre,4);

		Game game = new Game(oui,non);

		System.out.println("hand size : ");
		System.out.println(oui.getHero().getHand().size());
		System.out.println(non.getHero().getHand().size());
		System.out.println("test"+1);

	}
}