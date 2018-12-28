package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;

import exception.MiniHeartStoneException;

public class EngineInterface {
	public static HashMap<UUID,Game> allCurrentGame = new HashMap<UUID,Game>();
	
	public static Queue<Player> mmNoob;
	public static Queue<Player> mmMid;
	public static Queue<Player> mmPro;
	
	// ici faire du spring pour recup les noms les héros jouables
	public static ArrayList<String> getAllHeroDescription() {
		ArrayList<String> ar = new ArrayList<String>();
		return ar;
	}
	
	public static void wantPlay(int lvl,String pseudo, String heroStr) {
		Hero her = null;
		
		try {
			if (heroStr == "Mage") {
				her = new Magus();
			}
			else if (heroStr == "Guerrier") {
				her = new Warrior();
			}
			else if (heroStr == "Paladin") {
				her = new Paladin();
			}
			else {
				throw new MiniHeartStoneException("Vous devez Choisir Mage, Guerrier ou Paladin");
			}
		}
		catch(MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
		
		// création du player
		// lvl 1 = noob 2 = mid 3 = pro
		Player play = new Player(pseudo,her,lvl);
		
		try {
			if (lvl == 1) {
				if (mmNoob.poll() != null) {
					// création de la game
					Game gm = new Game(mmNoob.poll(),play);
					allCurrentGame.put(gm.getGameID(),gm);
				}
				else {
					mmNoob.add(play);
				}
			}
			else if (lvl == 2) {
				if (mmMid.poll() != null) {
					// création de la game
					Game gm = new Game(mmMid.poll(),play);
					allCurrentGame.put(gm.getGameID(),gm);
				}
				else {
					mmMid.add(play);
				}
			}
			else if (lvl == 3) {
				if (mmPro.poll() != null) {
					// création de la game
					Game gm = new Game(mmPro.poll(),play);
					allCurrentGame.put(gm.getGameID(),gm);
				}
				else {
					mmPro.add(play);
				}
			}
			else {
				throw new MiniHeartStoneException("Vous devez selectionner un de ces 3 niveaux : 1 pour noob, 2 pour mid, 3 pour pro");
			}
		}
		catch(MiniHeartStoneException e) {
			System.out.println(e.toString());
		}
	}
	
	// xd
	public static void invock(UUID gamUUID, UUID playerUUID, UUID cardID) {
		allCurrentGame.get(gamUUID).invock(playerUUID, cardID);
	}
	
	//lol
	public static void attack(UUID gamUUID, UUID playerUUID, UUID MycardUUID, UUID oppCardUUID) {
		allCurrentGame.get(gamUUID).attack(playerUUID, MycardUUID, oppCardUUID);
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
		allCurrentGame.get(gamUUID).passTurn(playerUUID);
	}
}
