package engine;

import java.util.UUID;

public class Player {
	
	protected String pseudo;
	protected Hero hero;
	protected int level;
	protected UUID playerID;
	
	public Player(String pseudo,  Hero hero, int level) {
		this.pseudo = pseudo;
		this.hero = hero;
		this.level = level;
		UUID playerID = UUID.randomUUID();
	}
	
	public Hero getHero() {
		return this.hero;
	}

	public String getPSeudo() {
		return this.pseudo;
	}

	public UUID getPlayerID() {
		return this.playerID;
	}

}
