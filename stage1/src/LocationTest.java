import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    private Location location1 = new Location(0,0);
    private Location location2 = new Location(1,3);
    private Location location3 = new Location(0,0);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateIllegalObject() {
        new Location(-1,0);
    }

    @Test
    public void testDistanceTo() {
        assertEquals(4, location1.distanceTo(location2));
    }

    @Test
    public void testEquals() {
        assertEquals(location1, location3);
        assertNotEquals(location1, location2);
    }
}