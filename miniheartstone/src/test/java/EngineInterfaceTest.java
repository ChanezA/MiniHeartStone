import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.UUID;

public class EngineInterfaceTest {

    @Test
    public final void wantPlayTest() {
        EngineInterface test = new EngineInterface();
        UUID uuidtest = test.wantPlay(1, "noob", "Mage");
        Player testPlay = test.mmNoob.poll();
        assertTrue(uuidtest.equals(testPlay.getPlayerID()));
        assertTrue(test.mmMid.isEmpty());
        assertTrue(test.mmPro.isEmpty());
        assertTrue(test.allCurrentGame.isEmpty());

        test.wantPlay(1, "noob", "Mage");
        test.wantPlay(2, "moyen", "Guerrier");
        assertFalse(test.mmNoob.isEmpty());
        assertFalse(test.mmMid.isEmpty());
        assertTrue(test.allCurrentGame.isEmpty());

        test.wantPlay(1, "noobGod", "Paladin");
        assertTrue(test.mmNoob.isEmpty());
        assertTrue(test.allCurrentGame.size() == 1);
        
    }
}
