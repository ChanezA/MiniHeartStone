import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

import engine.Hero_Doublure;
import engine.Minion;
import engine.Paladin_Doublure;
import exception.MiniHeartStoneException;
import engine.Card;


public class HeroTest {
	/*
	@Test (expected = exception.MiniHeartStoneException.class)
	public final void testPaladinException() {
		Hero_Doublure pal = new Paladin_Doublure();
    	pal.setMana(20);
        
		// test de levé d'exception si la carte n'est pas en main
        pal.invock(UUID.randomUUID());
	}*/
	
	 @Test
	 public final void testPaladinHasBeenAttack() {
	    Hero_Doublure pal = new Paladin_Doublure();
	    pal.setMana(20);
	    assertTrue(pal.getHealth() == 30);
	    Card crd1 = pal.draw("Sanglier brocheroc");
	    assertTrue(pal.getHand().size() == 1);
	        
	    Card crd2 = pal.draw("Chef de raid");
	    assertTrue(pal.getHand().size() == 2);
	        
	    // invocation du sanglier
	    pal.invock(crd1.getCardUUID());
	    assertTrue(((Minion)crd1).getAttack() == 1);
	    // invocation du chef de raid
	    pal.invock(crd2.getCardUUID());
	    assertTrue(((Minion)crd2).getAttack() == 2);
	    
	    assertTrue(((Minion)crd1).getAttack() == 2);
	    
	    // le chef de raid prend une patate de 1
	    pal.hasBeenAttack(crd2.getCardUUID(), 1);
	    // sa vie devrait donc etre a 1
	    assertTrue(((Minion)crd2).getLife() == 1);
	    
	    // on regarde si le sanglier repasse bien à 1 quand on kill le chef de raid
	    pal.hasBeenAttack(crd2.getCardUUID(), 1);
	    assertTrue(((Minion)crd1).getAttack() == 1);
	    
	    System.out.println(pal.superToString());
	 }

    @Test
    public final void testPaladinInvock() {
    	Hero_Doublure pal = new Paladin_Doublure();
    	pal.setMana(20);
        assertTrue(pal.getHealth() == 30);

        Card crd1 = pal.draw("Sanglier brocheroc");
        assertTrue(pal.getHand().size() == 1);
        
        Card crd2 = pal.draw("Chef de raid");
        assertTrue(pal.getHand().size() == 2);
        
        // invocation du sanglier
        pal.invock(crd1.getCardUUID());
        assertTrue(((Minion)crd1).getAttack() == 1);
        
        //invocation du chef de raid
        pal.invock(crd2.getCardUUID());
        assertTrue(((Minion)crd2).getAttack() == 2);
        
        assertTrue(((Minion)crd1).getAttack() == 2);
        
        //on pioche un sanglier
        Card crd3 = pal.draw("Sanglier brocheroc");
        assertTrue(pal.getHand().size() == 1);
        
        //on invoque ce sanglier
        pal.invock(crd3.getCardUUID());
        assertTrue(((Minion)crd3).getAttack() == 2);
        
        // On mioche Image miroir
        Card crd4 = pal.draw("Image miroir");
        assertTrue(pal.getHand().size() == 1);
        // On play la carte Imaghe miroir
        pal.invock(crd4.getCardUUID());
 
        //System.out.println(pal.superToString());
    }
}
