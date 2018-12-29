import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

public class SpellTest {

    @Test
    public final void cloneTest() {
        Spell spell = new Spell(2, "test", "Ceci est un spell test", "http://ceciestunspelltest.com/ntm");
        Spell clone = spell.cloneSpell();
        Spell clone2 = spell.cloneCard();
        assertTrue(spell.getDescription().equals(clone.getDescription()) && spell.getName().equals(clone.getName()) &&
                    spell.getManaCost() == clone.getManaCost() && spell.getPictureURL().equals(clone.getPictureURL()) &&
                    !spell.getCardUUID().equals(clone.getCardUUID()));
        assertTrue(spell.getDescription().equals(clone2.getDescription()) && spell.getName().equals(clone2.getName()) &&
                spell.getManaCost() == clone2.getManaCost() && spell.getPictureURL().equals(clone2.getPictureURL()) &&
                !spell.getCardUUID().equals(clone2.getCardUUID()));
    }
}
