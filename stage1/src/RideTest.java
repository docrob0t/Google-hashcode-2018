import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RideTest {

    private Ride ride0;
    private Ride ride1;
    private Ride ride2;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws Exception {
        ride0 = new Ride(0, new Location(0,0), new Location(1,3), 2, 9);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() throws Exception {
        ride0 = null;
        ride1 = null;
        ride2 = null;
    }

    /**
     * Test that a correct distance would be returned
     */
    @Test
    public void testCalculateDistance() {
        assertEquals(4, ride0.calculateDistance());
    }

    @Test(expected = NullPointerException.class)
    public void testNullPickupLocation() {
        ride1 = new Ride(1, null, new Location(1,0), 0, 9);
    }

    @Test(expected = NullPointerException.class)
    public void testNullDestinationLocation() {
        ride2 = new Ride(2, new Location(1,0), null, 0, 9);
    }
}