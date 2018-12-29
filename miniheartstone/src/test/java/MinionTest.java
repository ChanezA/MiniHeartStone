import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

public class MinionTest {

    @Test
    public final void cannotAttackTurnOneExceptIfHasCharge() {
        //Tester avec une carte ayant l'effet Charge aussi
        Minion minion = new Minion("test", "desc", 2, 3, 3, false, false, true, "null");
        assertTrue(minion.getReadyToAttack());
        Minion minion2 = new Minion("test", "desc", 2, 3, 3, false, false, false, "null");
        assertFalse(minion2.getReadyToAttack());
    }

    @Test
    public final void cloneTest() {
        Minion minion = new Minion("test", "desc", 2, 3, 3, false, false, true, "null");
        Minion clone = minion.cloneCard();
        assertTrue(minion.getName().equals(clone.getName()) && minion.getHasCharge() == clone.getHasCharge() && minion.getHasPrococation() == clone.getHasPrococation() &&
                    minion.getHasVolDeVie() == clone.getHasVolDeVie() && minion.getReadyToAttack() == clone.getReadyToAttack() && minion.getAttack() == clone.getAttack() &&
                    minion.getLife() == clone.getLife() && minion.getCardUUID() != clone.getCardUUID() && minion.getManaCost() == clone.getManaCost() &&
                    clone.getDescription().equals(minion.getDescription()) && minion.getPictureURL().equals(clone.getPictureURL()));
        Minion clone2 = minion.cloneMinion();
        assertTrue(minion.getName().equals(clone2.getName()) && minion.getHasCharge() == clone2.getHasCharge() && minion.getHasPrococation() == clone2.getHasPrococation() &&
                    minion.getHasVolDeVie() == clone2.getHasVolDeVie() && minion.getReadyToAttack() == clone2.getReadyToAttack() && minion.getAttack() == clone2.getAttack() &&
                    minion.getLife() == clone2.getLife() && minion.getCardUUID() != clone2.getCardUUID() && minion.getManaCost() == clone2.getManaCost() &&
                    clone2.getDescription().equals(minion.getDescription()) && minion.getPictureURL().equals(clone2.getPictureURL()));

    }
}
