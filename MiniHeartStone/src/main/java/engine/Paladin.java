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
	
	public static void main(String[] args) {
		
		Hero paladin = new Paladin();
		paladin.setMana(10);
		
		Card crd1 = paladin.draw();
		Card crd2 = paladin.draw();
		Card crd3 = paladin.draw();
		Card crd4 = paladin.draw();
		Card crd5 = paladin.draw();
		Card crd6 = paladin.draw();
		
		paladin.invock(crd1.getCardUUID());
		paladin.invock(crd2.getCardUUID());
		paladin.invock(crd3.getCardUUID());
		paladin.invock(crd4.getCardUUID());
		
		System.out.println(paladin.superToString());
		// je fais des modifs et ça commit pas wtf
		
	}
}
