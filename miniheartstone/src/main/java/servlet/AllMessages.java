package servlet;

public class AllMessages {
	String pseudo;
	String hero;
	int lvl;

	AllMessages() {}

	AllMessages(String pseudo, String hero, int lvl){
		this.pseudo = pseudo;
		this.hero = hero;
		this.lvl = lvl;
	}

	public String getPseudo() {
		return this.pseudo;
	}
	public String getHero() {
		return this.hero;
	}
	public int getlvl() {
		return this.lvl;
	}
}