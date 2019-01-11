import static org.junit.Assert.*;

import engine.*;
import org.junit.Test;

import java.util.Iterator;

public class EngineInterfaceTest {

    @Test
    public final void wantPlayTest() {
        EngineInterface test = new EngineInterface();
        test.wantPlay(1, "noob", "Mage");

    }
}
