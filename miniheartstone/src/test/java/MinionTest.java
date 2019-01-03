import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

public class MinionTest {

    @Test
    public final void cannotAttackTurnOneExceptIfHasCharge() {
        //Tester avec une carte ayant l'effet Charge aussi
        Minion minion = new Minion("test", "desc", 2, 3, 3, false, false, true, "null");
        assertTrue(minion.isReadyToAttack());
        Minion minion2 = new Minion("test", "desc", 2, 3, 3, false, false, false, "null");
        assertFalse(minion2.isReadyToAttack());
    }

    @Test
    public final void cloneTest() {
        Minion minion = new Minion("test", "desc", 2, 3, 3, false, false, true, "null");
        Minion clone = minion.cloneCard();
        assertTrue(minion.getName().equals(clone.getName()) && minion.getHasCharge() == clone.getHasCharge() && minion.getHasTaunt() == clone.getHasTaunt() &&
                    minion.getHasLifeSteal() == clone.getHasLifeSteal() && minion.isReadyToAttack() == clone.isReadyToAttack() && minion.getAttack() == clone.getAttack() &&
                    minion.getLife() == clone.getLife() && minion.getCardUUID() != clone.getCardUUID() && minion.getManaCost() == clone.getManaCost() &&
                    clone.getDescription().equals(minion.getDescription()) && minion.getPictureURL().equals(clone.getPictureURL()));
        Minion clone2 = minion.cloneCard();
        assertTrue(minion.getName().equals(clone2.getName()) && minion.getHasCharge() == clone2.getHasCharge() && minion.getHasTaunt() == clone2.getHasTaunt() &&
                    minion.getHasLifeSteal() == clone2.getHasLifeSteal() && minion.isReadyToAttack() == clone2.isReadyToAttack() && minion.getAttack() == clone2.getAttack() &&
                    minion.getLife() == clone2.getLife() && minion.getCardUUID() != clone2.getCardUUID() && minion.getManaCost() == clone2.getManaCost() &&
                    clone2.getDescription().equals(minion.getDescription()) && minion.getPictureURL().equals(clone2.getPictureURL()));

    }
}
