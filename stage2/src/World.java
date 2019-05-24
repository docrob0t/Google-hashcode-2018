import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Run the simulation by asking a collective of actors to act.
 */
public class World
{
    // Number of rows of the grid
    private int rows;
    // Number of columns of the grid
    private int cols;
    // Number of vehicles in the fleet
    private int totalNoOfVehicles;
    // Number of rides
    private int noOfRides;
    // All the vehicles in this world
    private List<Vehicle> fleet;
    // All the rides in this world
    private List<Ride> rides;
    // Per-ride bonus for starting the ride on time
    private int bonus;
    // Number of steps in the simulation
    private int noOfSteps;
    // private List<int[]> allocationArray;
    private String worldAndRidesFileName;

    /**
     * Constructor of World
     */
    public World(String worldAndRidesFileName) {
        rows = 0;
        cols = 0;
        totalNoOfVehicles = 0;
        noOfRides = 0;
        fleet = new ArrayList<>(totalNoOfVehicles);
        rides = new ArrayList<>();
        bonus = 0;
        noOfSteps = 0;
        // allocationArray = new ArrayList<>();
        this.worldAndRidesFileName = worldAndRidesFileName;
    }

    /**
     * Creating all vehicles and rides according to the input file.
     */
    public void initialise() {
        InputReader inputReader = new InputReader();
        ArrayList<int[]> file = inputReader.parseInput(worldAndRidesFileName);
        // For each line in the file
        for (int i = 0; i < file.size(); i++) {
            int[] line = file.get(i);
            // If it is the first line
            if (i == 0) {
                rows = line[0];
                cols = line[1];
                totalNoOfVehicles = line[2];
                noOfRides = line[3];
                bonus = line[4];
                noOfSteps = line[5];
                fleet = new ArrayList<>(totalNoOfVehicles);
                // Create new vehicles in the fleet
                for (int j = 0; j < totalNoOfVehicles; j++) {
                    // Create new Vehicle object
                    Vehicle vehicle = new Vehicle(j);
                    fleet.add(vehicle);
                }
            } else {
                int x1 = line[0];
                int y1 = line[1];
                int x2 = line[2];
                int y2 = line[3];
                int earliestStart = line[4];
                int latestFinish = line[5];
                Ride ride = new Ride(i - 1, new Location(x1, y1), new Location(x2, y2), earliestStart, latestFinish);
                rides.add(ride);
            }
        }
    }

    /**
     * Print out the solution to console
     */
    public void printSolution() {
        for (Vehicle vehicle: fleet) {
            List<Ride> ridesAssigned = vehicle.getRides();
            System.out.print(ridesAssigned.size());
            for (Ride ride: ridesAssigned) {
                System.out.print(" " + ride.getRid());
            }
            System.out.println();
        }
    }

    /**
     * Solve by sorting Car Time
     */
    public void solve() {
        // Sort all rides in order
        Collections.sort(rides);
        // Keep track of current step
        int currentStep = -1;
        // While there are rides in the list
        while (rides.size() > 0) {
            // Sort all vehicles in order
            Collections.sort(fleet);
            currentStep++;
            // If current step is less than number of steps in the simulation then continue
            if (currentStep < noOfSteps) {
                // Remove impossible rides in each step to increase searching speed
                removeImpossibleRide(currentStep);
                for (Vehicle vehicle : fleet) {
                    // If this car can do those rides in time, find the best ride in each step
                    if (vehicle.getCurrentStep() <= currentStep) {
                        findBestRide(currentStep, vehicle);
                    }
                }
            }
        }
    }

    /**
     * Remove rides that are impossible to complete from the list at this current step.
     *
     * @param currentStep The current step of the simulation
     */
    private void removeImpossibleRide(int currentStep) {
        for (Iterator<Ride> iterator = rides.iterator(); iterator.hasNext();) {
            Ride ride = iterator.next();
            // If ride is not possible
            if (ride.getLatestStart() < currentStep) {
                // Remove the current element from the iterator and the list.
                iterator.remove();
            }
        }
    }

    /**
     * Find the best ride for this vehicle at this particular time
     *
     * @param currentTime
     * @param vehicle The vehicle to be evaluated
     */
    private void findBestRide(int currentTime, Vehicle vehicle) {
        Ride bestRide = null;
        int bestStartTime = 0;

        for (Ride ride : rides) {
            // If this ride could start at currentTime
            if (ride.getEarliestStart() <= currentTime) {
                // Finds out the step where the vehicle drove to the starting point of the ride
                int vehicleToStart = vehicle.getCurrentStep() + vehicle.distanceToRideStart(ride);
                int rideLatestFinish = ride.getLatestFinish();
                // If the starting step of the ride is less than currentTime and the latest finish
                if (vehicleToStart <= currentTime && vehicleToStart <= rideLatestFinish) {
                    // If the ride could be finished before the latest finish
                    if (vehicle.arrival(ride) < rideLatestFinish) {
                        if (bestRide == null || ride.getDistance() < bestRide.getDistance()) {
                            bestRide = ride;
                            bestStartTime = Math.max(vehicleToStart, ride.getEarliestStart());
                        }
                    }
                }
            } else {
                break;
            }
        }

        if (bestRide != null) {  // Check if valid
            addBestRide(vehicle, bestRide, bestStartTime);
        }
    }

    private void addBestRide(Vehicle vehicle, Ride bestRide, int bestStartTime) {
        // Find the earliest finishing time for the best ride
        int bestRideEndTime = bestStartTime + bestRide.getDistance();
        // Complete ride on time
        if (bestRideEndTime < bestRide.getLatestFinish()) {
            if (bestRideEndTime < noOfSteps) {
                // Add ride to car
                vehicle.addRide(bestRide, bestRideEndTime);
                rides.remove(bestRide);
            }
        }
    }
}
