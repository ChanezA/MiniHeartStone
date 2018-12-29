import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

public class MinionTest {

    @Test
    public final void cannotAttackTurnOneExceptIfHasCharge() {
        //Tester avec une carte ayant l'effet Charge aussi
        Minion minion = new Minion("test", "desc", 2, 3, 3, false, false, true, "null");


    }
}
