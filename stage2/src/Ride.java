/**
 * Model the rides.
 * A ride holds the starting and finishing coordinates of the ride, the earliest start step, and the latest finish step.
 */
public class Ride implements Comparable<Ride>
{
    // The ID of each ride
    private int rid;
    // The coordinate of the starting point
    private Location pickup;
    // The coordinate of the finishing point
    private Location destination;
    // The earliest start of the ride
    private int earliestStart;
    // The latest start of the ride
    private int latestStart;
    // The latest finish of the ride
    private int latestFinish;
    // The distance of this ride, the length from pick up to destination
    private int distance;

    /**
     * Constructor of Ride
     *
     * @param rid the ID of this ride
     * @param pickup the location of the starting point
     * @param destination the location of the finishing point
     * @param earliestStart the earliest start of the ride
     * @param latestFinish the latest finish of the ride
     */
    public Ride(int rid, Location pickup, Location destination, int earliestStart, int latestFinish) {
        this.rid = rid;
        if(pickup == null) {
            throw new NullPointerException("Pickup location");
        }
        if(destination == null) {
            throw new NullPointerException("Destination location");
        }
        this.pickup = pickup;
        this.destination = destination;
        this.earliestStart = earliestStart;
        this.latestFinish = latestFinish;
        distance = calculateDistance();
        this.latestStart = this.latestFinish - this.distance;
    }

    /**
     * Calculate the distance between the starting point and the finish point
     *
     * @return The Manhattan distance between the starting point and the finish point
     */
    public int calculateDistance() {
        return pickup.distanceTo(destination);
    }

    /**
     * Return details of this ride, such as where the starting point and finishing point are.
     *
     * @return a string representation of this ride
     */
    @Override
    public String toString() {
        return "Ride {" +
                "id=" + rid +
                ", Starting coordinate:" + pickup.toString() +
                ", Finishing coordinate:"+ destination.toString() +
                ", Distance:"+ distance +
                ", earlierStart:" + earliestStart +
                ", latestFinish:" + latestFinish +
                '}';
    }

    // Getters and setters for fields
    public int getEarliestStart() {
        return earliestStart;
    }

    public int getLatestStart() {
        return latestStart;
    }

    public int getLatestFinish() {
        return latestFinish;
    }

    /**
     * @return The pickup location.
     */
    public Location getPickupLocation()
    {
        return pickup;
    }

    /**
     * @return The destination location.
     */
    public Location getDestination()
    {
        return destination;
    }

    public int getRid() {
        return rid;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Compares this ride with earliest start time first, then compare the latest finish time,
     * then compare the distance
     *
     * @param o The ride to be compared
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
     *         or greater than the specified object.
     */
    @Override
    public int compareTo(Ride o) {
        //int compareStart = o1.earliestStart.compareTo(o2.earliestStart);
        int compareStart = Integer.compare(earliestStart, o.earliestStart);
        if (compareStart != 0) {
            return compareStart;
        }

        //int compareEnd = o1.latestFinish.compareTo(o2.latestFinish);
        int compareEnd = Integer.compare(latestFinish, o.latestFinish);
        if (compareEnd != 0) {
            return compareEnd;
        }

        return Integer.compare(distance, o.distance);
    }
}