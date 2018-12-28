package engine;
// test
public class Paladin_Doublure extends Hero_Doublure {
	
	public Paladin_Doublure() {
		super("Paladin", 30, "mdr je suis un palalol");
	}
	
	
	public void power() {
		Card card = new Minion("Recrue de la Main d'argent", "je suis la pour LE swag",0, 1, 1, false,false, false, null);
		((Minion)card).setAttack(((Minion)card).getAttack()+ this.howManyChefDeRaidInMyBoard());
		this.getBoard().add(card);
	}
	
	public String toString() {
		return "I'am Paladin, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
	
}