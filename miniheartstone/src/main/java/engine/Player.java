package engine;

import java.util.UUID;

public class Player {
	
	protected String name;
	protected AbstractHero hero;
	protected int level;
	protected UUID playerID;
	
	public Player(String name, AbstractHero hero, int level) {
		this.name = name;
		this.hero = hero;
		this.level = level;
		UUID playerID = UUID.randomUUID();
	}
	
	public AbstractHero getHero() {
		return this.hero;
	}

	public String getName() {
		return this.name;
	}

	public UUID getPlayerID() {
		return this.playerID;
	}

}
