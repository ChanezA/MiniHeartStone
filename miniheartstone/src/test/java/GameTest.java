import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

public class GameTest {

    /**
     * Test de l'initialisation d'une game (on vérifie que le player courant soit le bon, que les héros aiennt les bonnes statistiques)
     */
    @Test
    public final void initGameTest() {
        Player player1 = new Player("p1", new Magus(), 1);
        Player player2 = new Player("p2", new Warrior(), 1);
        Game game = new Game(player1, player2);
        assertTrue(game.getCurrentPlayer().equals(game.getPlayer1()));
        assertTrue(game.getNotCurrentPlayer().equals(game.getPlayer2()));
        assertTrue(game.getCurrentPlayer().getHero().getMana() == 1);
        assertTrue(game.getNotCurrentPlayer().getHero().getMana() == 1);
        assertTrue(game.getCurrentPlayer().getHero().getHealth() == game.getNotCurrentPlayer().getHero().getHealth());
    }

}
