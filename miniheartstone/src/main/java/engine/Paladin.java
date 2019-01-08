package engine;

public class Paladin extends AbstractHero {
	
	public Paladin() {
		super("Paladin", 30, "mdr je suis un palalol");
	}
	
	
	public void power() {
		AbstractCard abstractCard = new Minion("Recrue de la Main d'argent", "je suis la pour LE swag",0, 1, 1, false,false, false,null, null);
		((Minion) abstractCard).setAttack(((Minion) abstractCard).getAttack()+ this.howManyChefDeRaidInMyBoard());
		this.getBoard().add(abstractCard);
	}
	
	public String toString() {
		return "I'am Paladin, name: " + this.getHeroName() + " mana: "+this.mana +" health: "+this.getHealth() +" armor: "+ this.getArmor();
	}
	
	public static void main(String[] args) {
		
		AbstractHero paladin = new Paladin();
		paladin.setMana(10);
		
		AbstractCard crd1 = paladin.draw();
		AbstractCard crd2 = paladin.draw();
		AbstractCard crd3 = paladin.draw();
		AbstractCard crd4 = paladin.draw();
		AbstractCard crd5 = paladin.draw();
		AbstractCard crd6 = paladin.draw();
		
		paladin.invock(crd1.getCardUUID());
		paladin.invock(crd2.getCardUUID());
		paladin.invock(crd3.getCardUUID());
		paladin.invock(crd4.getCardUUID());
		
		System.out.println(paladin.superToString());
		// je fais des modifs et ça commit pas wtf
		
	}
}
