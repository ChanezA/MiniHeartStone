package engine;

public class Paladin extends Hero {
	
	public Paladin() {
		super("Paladin", 30, "mdr je suis un palalol");
	}
	
	
	public void power() {
		Card card = new Minion(1, 1,0, "Recrue de la Main d'argent", "je suis la pour LE swag", null,false, false, false);
		((Minion)card).setAttack(((Minion)card).getAttack()+ this.howManyChefDeRaidInMyBoard());
		this.getBoard().add(card);
		
	}
	
	public String toString() {
		return "I'am Paladin, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
}
