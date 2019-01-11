import static org.junit.Assert.assertTrue;

import engine.AbstractHero;
import engine.Paladin;
import exception.MiniHeartStoneException;
import org.junit.Test;

import engine.Minion;
import engine.AbstractCard;


public class AbstractHeroTest {
	/*
	@Test (expected = exception.MiniHeartStoneException.class)
	public final void testPaladinException() {
		AbstractHeroDoublure pal = new PaladinDoublure();
    	pal.setMana(20);
        
		// test de levé d'exception si la carte n'est pas en main
        pal.invock(UUID.randomUUID());
	}*/
	
	 @Test
	 public final void testPaladinHasBeenAttack() {
	    AbstractHero pal = new Paladin();
	    pal.setMana(20);
	    assertTrue(pal.getHealth() == 30);
	    AbstractCard crd1 = pal.draw("Sanglier brocheroc");
	    assertTrue(pal.getHand().size() == 1);
	        
	    AbstractCard crd2 = pal.draw("Chef de raid");
	    assertTrue(pal.getHand().size() == 2);
	        
	    // invocation du sanglier
		 try {
			 pal.invock(crd1.getCardUUID());
		 } catch (MiniHeartStoneException e) { e.printStackTrace(); }
	    assertTrue(((Minion)crd1).getAttack() == 1);
	    // invocation du chef de raid
		 try {
			 pal.invock(crd2.getCardUUID());
		 } catch (MiniHeartStoneException e) { e.printStackTrace(); }
	    assertTrue(((Minion)crd2).getAttack() == 2);
	    
	    assertTrue(((Minion)crd1).getAttack() == 2);
	    
	    // le chef de raid prend une patate de 1
	    pal.hasBeenAttack(crd2.getCardUUID(), 1);
	    // sa vie devrait donc etre a 1
	    assertTrue(((Minion)crd2).getLife() == 1);
	    
	    // on regarde si le sanglier repasse bien à 1 quand on kill le chef de raid
	    pal.hasBeenAttack(crd2.getCardUUID(), 1);
	    assertTrue(((Minion)crd1).getAttack() == 1);
	    
	    // mon hero prned une patate de 10
	    pal.myHeroHasBeenAttack(10);
	    assertTrue(pal.getHealth() == 20);
	    assertTrue(pal.isDead() == false);
	    
	 // mon hero prned une patate de 20
	    pal.myHeroHasBeenAttack(20);
	    assertTrue(pal.getHealth() == 0);
	    
	    assertTrue(pal.isDead() == true);
	    
	   
	    AbstractCard crd4 = pal.draw("Chef de raid");
	    // invocation du chef de raid
	    try {
	    	pal.invock(crd4.getCardUUID());
		}catch (MiniHeartStoneException e) { e.printStackTrace(); }
	    
	    // utilisation du pouvoir du paladin
	    pal.power();
	    
	    System.out.println(pal.superToString());
	 }

    @Test
    public final void testPaladinInvock() {
    	AbstractHero pal = new Paladin();
    	pal.setMana(20);
        assertTrue(pal.getHealth() == 30);

        AbstractCard crd1 = pal.draw("Sanglier brocheroc");
        assertTrue(pal.getHand().size() == 1);
        
        AbstractCard crd2 = pal.draw("Chef de raid");
        assertTrue(pal.getHand().size() == 2);
        
        // invocation du sanglier
        try {
        	pal.invock(crd1.getCardUUID());
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
        assertTrue(((Minion)crd1).getAttack() == 1);
        
        //invocation du chef de raid
        try {
        	pal.invock(crd2.getCardUUID());
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
        assertTrue(((Minion)crd2).getAttack() == 2);
        
        assertTrue(((Minion)crd1).getAttack() == 2);
        
        //on pioche un sanglier
        AbstractCard crd3 = pal.draw("Sanglier brocheroc");
        assertTrue(pal.getHand().size() == 1);
        
        //on invoque ce sanglier
        try {
        	pal.invock(crd3.getCardUUID());
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
        assertTrue(((Minion)crd3).getAttack() == 2);
        
        // On mioche Image miroir
        AbstractCard crd4 = pal.draw("Image miroir");
        assertTrue(pal.getHand().size() == 1);
        // On play la carte Imaghe miroir
        try {
        	pal.invock(crd4.getCardUUID());
		} catch (MiniHeartStoneException e) { e.printStackTrace(); }
 
        //System.out.println(pal.superToString());
    }
}
