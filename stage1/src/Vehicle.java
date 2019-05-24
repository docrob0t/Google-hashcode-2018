import java.util.*;

/**
 * Model the vehicle.
 * A vehicle is holding a list of assigned rides and the current location of the vehicle
 */
public class Vehicle{
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

    /**
     * Check if this vehicle can start the assigned ride on time.
     * Return true if this vehicle can, false otherwise.
     *
     * @param ride The ride that is assigned to this vehicle
     * @return true if this vehicle can start the assigned ride on time, false otherwise.
     */
    public boolean canStartOnTime(Ride ride) {
        return (this.currentStep + this.distanceToRideStart(ride)) <= ride.getEarliestStart();
    }

    /**
     * Check if this vehicle can finish the assigned ride in time.
     * Return true if this vehicle can finish the ride in time, false otherwise.
     *
     * @param ride The ride that is assigned to this vehicle
     * @param world The world where the simulations happens
     * @return true if this vehicle can finish the assigned ride in time, false otherwise.
     */
    public boolean canFinishInTime(Ride ride, World world) {
        boolean canFinish = (this.arrival(ride) <= Math.min(ride.getLatestFinish(), world.getNoOfSteps()));
        return canFinish;
    }

    /**
     * Assign a new position for this vehicle to the finishing point of the ride
     *
     * @param ride the ride that assigned to the vehicle
     */
    public void assignRide(Ride ride) {
        rides.add(ride);
        // The realistic earliest start, the step which the vehicle departures
        int earliestStart = ride.getEarliestStart();
        int earliestArrivalStep = this.currentStep + this.distanceToRideStart(ride);
        int stepDeparture = Math.max(earliestStart, earliestArrivalStep);
        this.currentStep = stepDeparture + ride.calculateDistance();
        // When the Vehicle arrives the destination, the current location
        // would be the same as the finishing point of the ride
        Location destination = ride.getDestination();
        setLocation(destination);
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


    // Getters and setters for fields
    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    /**
     * Set the current location.
     * @param location Where it is. Must not be null.
     * @throws NullPointerException If location is null.
     */
    public void setLocation(Location location)
    {
        if(location != null) {
            this.location = location;
        }
        else {
            throw new NullPointerException();
        }
    }

}
