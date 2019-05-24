import java.util.ArrayList;

/**
 * Model the vehicle.
 * A vehicle is holding a list of assigned rides and the current location of the vehicle
 */
public class Vehicle implements Comparable<Vehicle>
{
    // ID of each vehicle
    private int id;
    // List of assigned rides
    private ArrayList<Ride> rides;
    // The current step of the ride
    private int currentStep;
    // Where the vehicle is.
    private Location location;

    /**
     * Constructor of class Vehicle
     *
     * @param id Vehicle ID
     */
    public Vehicle(int id) {
        this.id = id;
        this.rides = new ArrayList<>();
        this.currentStep = 0;
        this.location = new Location(0,0);
    }

    /**
     * Calculate the distance from the current location of this vehicle to the starting point
     *
     * @param ride The ride that is assigned to this vehicle
     * @return the distance need to go to the starting point
     */
    public int distanceToRideStart(Ride ride) {
        Location pickup = ride.getPickupLocation();
        return location.distanceTo(pickup);
    }

    /**
     * Calculate the maximum waiting time for this vehicle to start that ride that is assigned for.
     *
     * @param ride The ride that is assigned to this vehicle
     * @return the waiting time for this vehicle to start the ride
     */
    public int waitTime(Ride ride) {
        int max = Math.max(0, ride.getEarliestStart() - (this.currentStep + this.distanceToRideStart(ride)));
        return max;
    }

    /**
     * Calculate the finishing step when this vehicle finishes the assigned ride.
     * It calculate th number of steps required to finish the ride, counting from the start, that is currentStep = 0
     *
     * @param ride The ride that is assigned to this vehicle
     * @return the number of steps to finish the ride
     */
    public int arrival(Ride ride) {

        int step = this.currentStep;
        int distanceToRideStart = this.distanceToRideStart(ride);
        int waitTime = this.waitTime(ride);
        int distance = ride.calculateDistance();

        return step + distanceToRideStart + waitTime + distance;
    }

    public void addRide(Ride bestRide, int currentStep) {
        rides.add(bestRide);
        location = bestRide.getDestination();
        this.currentStep = currentStep;
    }

    // Getters and setters for fields
    public ArrayList<Ride> getRides() {
        return rides;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    /**
     * Return details of the vehicle, such as where it is.
     *
     * @return a string representation of the vehicle
     */
    @Override
    public String toString() {
        return "Vehicle {" +
                "id=" + id +
                ", Location:" + location.toString() +
                '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Vehicle o) {
        return Integer.compare(currentStep, o.currentStep);
    }

}
