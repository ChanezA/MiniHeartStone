import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.Hero_Doublure;
import engine.Minion;
import engine.Paladin_Doublure;
import engine.Card;

public class HeroTest {

    @Test
    public final void testPaladin() {
    	Hero_Doublure pal = new Paladin_Doublure();
        assertTrue(pal.getHealth() == 30);

        Card crd1 = pal.draw("Sanglier brocheroc");
        assertTrue(pal.getHand().size() == 1);
        
        Card crd2 = pal.draw("Chef de raid");
        assertTrue(pal.getHand().size() == 2);
        
        pal.invock(crd1.getCardUUID());
        assertTrue(((Minion)crd1).getAttack() == 1);
        
        pal.invock(crd2.getCardUUID());
        assertTrue(((Minion)crd2).getAttack() == 2);
        
        assertTrue(((Minion)crd1).getAttack() == 2);
        
    }
}
