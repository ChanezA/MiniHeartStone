import static org.junit.Assert.assertTrue;

import org.junit.Test;

import engine.Hero;
import engine.Paladin;

public class HeroTest {
	
	@Test
	public final void testPaladin() {
		Hero pal = new Paladin();
		assertTrue(pal.getHealth() == 30);
	}
}
