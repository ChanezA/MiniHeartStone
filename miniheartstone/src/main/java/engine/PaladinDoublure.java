package engine;
// test
public class PaladinDoublure extends AbstractHeroDoublure {
	
	public PaladinDoublure() {
		super("Paladin", 30, "mdr je suis un palalol");
	}
	
	
	public void power() {
		AbstractCard abstractCard = new Minion("Recrue de la Main d'argent", "je suis la pour LE swag",0, 1, 1, false,false, false, null);
		((Minion) abstractCard).setAttack(((Minion) abstractCard).getAttack()+ this.howManyChefDeRaidInMyBoard());
		this.getBoard().add(abstractCard);
	}
	
	public String toString() {
		return "I'am Paladin, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
	
}