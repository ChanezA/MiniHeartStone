package engine;

import java.util.UUID;

public class Player {
	
	protected String name;
	protected Hero hero;
	protected int level;
	protected UUID playerID;
	
	public Player(String name,  Hero hero, int level) {
		this.name = name;
		this.hero = hero;
		this.level = level;
		UUID playerID = UUID.randomUUID();
	}
	
	public Hero getHero() {
		return this.hero;
	}

	public String getName() {
		return this.name;
	}

	public UUID getPlayerID() {
		return this.playerID;
	}

}
