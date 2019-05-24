import java.util.*;

/**
 * Run the simulation by asking a collective of actors to act.
 */
public class World {

    // Number of rows of the grid
    private int rows;
    // Number of columns of the grid
    private int cols;
    // Number of vehicles in the fleet
    private int noOfVehicles;
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
    // Allocated rides
    private List<int[]> allocationArray;

    private String worldAndRidesFileName, allocationFileName;

    private InputReader inputReader;

    /**
     * Constructor of World
     */
    public World(String worldAndRidesFileName, String allocationFileName) {
        rows = 0;
        cols = 0;
        noOfVehicles = 0;
        noOfRides = 0;
        fleet = new ArrayList<>(noOfVehicles);
        rides = new ArrayList<>();
        bonus = 0;
        noOfSteps = 0;
        allocationArray = new ArrayList<>();
        this.worldAndRidesFileName = worldAndRidesFileName;
        this.allocationFileName = allocationFileName;
        inputReader = new InputReader();
    }

    /**
     * Creating all vehicles and rides according to the input file.
     */
    public void initialise() {
        readWorldAndRidesFile();
        readOutputFile();
    }

    /**
     * Calculate the total score of the allocation
     *
     * @return the total score of the allocation
     */
    public int calculateScore() {
        Score score = new Score();
        // Go through each vehicle in the fleet
        for (int i = 0; i < fleet.size(); i++) {
            Vehicle vehicle = fleet.get(i);
            // Get the allocated rides for that corresponding vehicle
            int[] allocatedRides = allocationArray.get(i);
            // Evaluate each ride that is assigned to that vehicle
            for (int rid : allocatedRides) {
                Ride ride = rides.get(rid);
                evaluateRide(vehicle, ride, score, this.bonus);
            }
        }
        return score.total();
    }

    /**
     * Evaluate a ride that is assigned to the vehicle
     *
     * Return true if the ride can finish in time, false if the ride is late.
     *
     * @param vehicle the vehicle that
     * @param ride the ride assigned to the vehicle
     * @param score the score
     * @param bonus the bonus points as specified by the input file
     * @return true if the ride can finish in time, false if the ride is late.
     */
    public void evaluateRide(Vehicle vehicle, Ride ride, Score score, int bonus) {
        score.incrementTaken();
        if (vehicle.canFinishInTime(ride, this)) {
            if (vehicle.canStartOnTime(ride)) {
                score.incrementBonusScore(bonus);
                score.incrementWaitTime(vehicle.waitTime(ride));
                score.incrementBonus();
            }
            score.incrementDistanceScore(ride.calculateDistance());
            vehicle.assignRide(ride);
        } else { // The ride is late
            vehicle.setCurrentStep(vehicle.arrival(ride));
            Location destination = ride.getDestination();
            vehicle.setLocation(destination);
            score.incrementLate();
        }
    }

    /**
     * If an error message is printed, stop execution.
     *
     * @param message The error message to be printed out
     */
    public void printError(String message) {
        System.out.println(message);
        System.exit(0);
    }

    /**
     * Check if number of vehicles found in output file matches with input file
     */
    public String checkVehicleNo() {
        if (noOfVehicles != allocationArray.size()) {
            return "Found " + allocationArray.size() + " cars in output file, expected " + noOfVehicles;
        }
        return null;
    }

    /**
     * Check total number of rides in a line matches with the first integer
     */
    public String checkNoOfRides() {
        int lineNo = 0;
        ArrayList<int[]> allocationArrayWithoutNumberOfRides = new ArrayList<>();
        for (int k = 0; k < allocationArray.size(); k++) {
            // Keep track of the line number
            lineNo++;
            int[] ridesLine = allocationArray.get(k);
            if (ridesLine[0] != (ridesLine.length - 1)) {
                return "Line " + lineNo + ": Declared " + ridesLine[0] + " rides but found " + (ridesLine.length - 1) + " rides";
            } else {
                // Skip the first integer and add them in an array
                ridesLine = Arrays.stream(ridesLine).skip(1).toArray();
                allocationArrayWithoutNumberOfRides.add(ridesLine);
            }
        }
        this.allocationArray = allocationArrayWithoutNumberOfRides;
        return null;
    }

    /**
     * Check if rides in the output file match with input file, are valid, and only assigned once
     * Check if a ride is assigned to two or more different vehicles, or to one vehicle more than once
     */
    public String checkRideID() {
        int index = 0;
        ArrayList<Integer> assignedRides = new ArrayList<>();
        for (int[] allocatedRides: allocationArray) {
            index++;
            for (int rid: allocatedRides) {
                if (rid < 0 || rid >= noOfRides) {
                    return "Line " + index + ": Invalid Ride ID "+ rid + ", expected from 0 to " + (noOfRides - 1);
                }
                else if (assignedRides.contains(rid)) {
                    return "Ride " + rid + " was assigned more than once";
                } else {
                    assignedRides.add(rid);
                }
            }
        }
        return null;
    }

    // Getters and setters for each field
    public int getNoOfSteps() {
        return noOfSteps;
    }

    public void setNoOfVehicles(int noOfVehicles) {
        this.noOfVehicles = noOfVehicles;
    }

    public void setNoOfRides(int noOfRides) {
        this.noOfRides = noOfRides;
    }

    public void setAllocationArray(List<int[]> allocationArray) {
        this.allocationArray = allocationArray;
    }

    // Private methods
    private void readWorldAndRidesFile() {
        ArrayList<int[]> file = inputReader.parseInput(worldAndRidesFileName);
        // For each line in the file
        for (int i = 0; i < file.size(); i++) {
            int[] line = file.get(i);
            // If it is the first line
            if (i == 0) {
                rows = line[0];
                cols = line[1];
                noOfVehicles = line[2];
                noOfRides = line[3];
                bonus = line[4];
                noOfSteps = line[5];

                fleet = new ArrayList<>(noOfVehicles);
                // Create new vehicles in the fleet
                for (int j = 0; j < noOfVehicles; j++) {
                    // Create new Vehicle object
                    Vehicle vehicle = new Vehicle(j);
                    // Test if values in the txt file has transferred to the objects' fields
                    // System.out.println(vehicle.toString());
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
                // Test if values in the txt file has transferred to the objects' fields
                // System.out.println(ride.toString());
                rides.add(ride);
            }
        }
    }

    private void readOutputFile() {
        // Read the output file, including the first integer - number of rides assigned to the vehicle
        allocationArray = inputReader.parseInput(allocationFileName);
    }
}
