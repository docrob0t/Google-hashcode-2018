import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RideTest {

    private Ride ride1;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws Exception {
        ride1 = new Ride(0, new Location (0,0), new Location (1,3) ,2,9);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test that a correct distance would be returned
     */
    @Test
    public void testCalculateDistance() {

        assertEquals(4, ride1.calculateDistance());
    }

    @Test
    public void testCompareTo() {
    }
}