import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WorldTest {

    private World world;
    private List<int[]> mockAllocationArray;
    private String message;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws Exception {
        world = new World("dummyString1", "dummyString2");
        int[] vehicle1 = new int[]{2, 1};
        mockAllocationArray = new ArrayList<>();
        mockAllocationArray.add(vehicle1);
        world.setNoOfVehicles(2);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() throws Exception {

    }

    // Tests for checkVehicleNo()
    @Test
    public void testLessVehicleThanExpected() {
        world.setAllocationArray(mockAllocationArray);
        message = world.checkVehicleNo();
        assertEquals("Found 1 cars in output file, expected 2", message);
    }

    @Test
    public void testVehicleNumberAsExpected() {
        int[] vehicle2 = new int[]{1};
        mockAllocationArray.add(vehicle2);
        world.setAllocationArray(mockAllocationArray);
        message = world.checkVehicleNo();
        assertEquals(null, message);
    }

    @Test
    public void testMoreVehicleThanExpected() {
        int[] vehicle2 = new int[]{1};
        mockAllocationArray.add(vehicle2);
        int[] vehicle3 = new int[]{0};
        mockAllocationArray.add(vehicle3);
        world.setAllocationArray(mockAllocationArray);
        message = world.checkVehicleNo();
        assertEquals("Found 3 cars in output file, expected 2", message);
    }

    // Tests for checkNoOfRides()
    @Test
    public void testFoundMoreRidesThanDeclared() {
        List<int[]> faultyRidesAllocationArray = new ArrayList<>();
        int[] vehicle1 = new int[]{2, 2, 1, 0};
        faultyRidesAllocationArray.add(vehicle1);
        world.setAllocationArray(faultyRidesAllocationArray);
        message = world.checkNoOfRides();
        assertEquals("Line 1: Declared 2 rides but found 3 rides", message);
    }

    @Test
    public void testFoundLessRidesThanDeclared() {
        List<int[]> faultyRidesAllocationArray = new ArrayList<>();
        int[] vehicle1 = new int[]{2, 2};
        faultyRidesAllocationArray.add(vehicle1);
        world.setAllocationArray(faultyRidesAllocationArray);
        message = world.checkNoOfRides();
        assertEquals("Line 1: Declared 2 rides but found 1 rides", message);
    }

    @Test
    public void testFoundRidesAsDeclared() {
        List<int[]> RidesAllocationArray = new ArrayList<>();
        int[] vehicle1 = new int[]{2, 2, 1};
        RidesAllocationArray.add(vehicle1);
        world.setAllocationArray(RidesAllocationArray);
        message = world.checkNoOfRides();
        assertEquals(null, message);
    }

    // Tests for checkRideID()
    @Test
    public void testLowerBoundaryRideID() {
        int[] vehicle2 = new int[]{-1};
        mockAllocationArray.add(vehicle2);
        world.setAllocationArray(mockAllocationArray);
        world.setNoOfRides(3);
        message = world.checkRideID();
        assertEquals("Line 2: Invalid Ride ID -1, expected from 0 to 2", message);
    }

    @Test
    public void testCheckRideID() {
        int[] vehicle2 = new int[]{0};
        mockAllocationArray.add(vehicle2);
        world.setAllocationArray(mockAllocationArray);
        world.setNoOfRides(3);
        message = world.checkRideID();
        assertEquals(null, message);
    }

    @Test
    public void testUpperBoundaryRideID() {
        int[] vehicle2 = new int[]{3};
        mockAllocationArray.add(vehicle2);
        world.setAllocationArray(mockAllocationArray);
        world.setNoOfRides(3);
        message = world.checkRideID();
        assertEquals("Line 2: Invalid Ride ID 3, expected from 0 to 2", message);
    }

    @Test
    public void testDuplicatedRideID() {
        int[] vehicle2 = new int[]{2};
        mockAllocationArray.add(vehicle2);
        world.setAllocationArray(mockAllocationArray);
        world.setNoOfRides(3);
        message = world.checkRideID();
        assertEquals("Ride 2 was assigned more than once", message);
    }
}