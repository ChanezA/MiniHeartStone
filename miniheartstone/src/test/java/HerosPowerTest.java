import engine.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HerosPowerTest {

    @Test
    public final void paladinPowerTestPass() {
        Player playerTest1 = new Player("PlayerTest1", new Paladin(), 1);
        Player playerTest2 = new Player("PlayerTest2", new Paladin(), 1);
        Game game = new Game(playerTest1, playerTest2);
        playerTest1.getHero().setMana(2);
        game.power(playerTest1.getPlayerID());
        assertTrue(playerTest1.getHero().getBoard().get(0).getName().equals("Recrue de la Main d'argent"));
    }

    @Test
    public final void paladinPowerTestFail() {
        Player playerTest1 = new Player("PlayerTest1", new Paladin(), 1);
        Player playerTest2 = new Player("PlayerTest2", new Paladin(), 1);
        Game game = new Game(playerTest1, playerTest2);
        playerTest1.getHero().setMana(4);
        game.power(playerTest1.getPlayerID());
        game.power(playerTest1.getPlayerID());
        assertTrue(playerTest1.getHero().getBoard().size() == 1);
        assertTrue(playerTest1.getHero().getMana() == 2);
    }

    //A faire : tests pour warrior et mage ; Mage besoin de mock pour le terrain
}
