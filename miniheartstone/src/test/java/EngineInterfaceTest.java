import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

import java.util.Iterator;
import java.util.UUID;

public class EngineInterfaceTest {

    /**
     * Test de la méthode wantPlay, qui fait en sorte que deux joueurs dans la même file puissent jouer entre eux.
     * On vérifie bien que les joueurs soient placés dans la file correspondante à leur niveau, et qu'une fois deux
     * joueurs au moins soint dans la même file, ces derniers puissent lancer une game et sont retirés de la file.
     */
    @Test
    public final void wantPlayTest() {
        EngineInterface test = new EngineInterface();
        UUID uuidtest = test.wantPlay(1, "noob", "Mage", null);
        Player testPlay = test.mmNoob.poll();
        assertTrue(uuidtest.equals(testPlay.getPlayerID()));
        assertTrue(test.mmMid.isEmpty());
        assertTrue(test.mmPro.isEmpty());
        assertTrue(test.allCurrentGame.isEmpty());

        test.wantPlay(1, "noob", "Mage", null);
        test.wantPlay(2, "moyen", "Guerrier", null);
        assertFalse(test.mmNoob.isEmpty());
        assertFalse(test.mmMid.isEmpty());
        assertTrue(test.allCurrentGame.isEmpty());

        test.wantPlay(1, "noobGod", "Paladin", null);
        assertTrue(test.mmNoob.isEmpty());
        assertTrue(test.allCurrentGame.size() == 1);
        
    }
}
