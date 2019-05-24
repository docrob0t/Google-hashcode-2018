/**
 * The Score class stores the score for each simulation
 */
public class Score {
    // Points obtained from distance
    private int distanceScore;
    // Points obtained from bonus
    private int bonusScore;
    // Number of rides that are taken
    private int taken;
    // Number of rides that are unassigned
    private int unassigned;
    // Number of rides that are late (late arrival)
    private int late;
    // Number of rides that departs on time
    private int bonus;
    // Total wait time
    private int waitTime;

    /**
     * Creates a score object with every field is 0.
     */
    public Score() {
        this.distanceScore = 0;
        this.bonusScore = 0;
        this.taken = 0;
        this.unassigned = 0;
        this.late = 0;
        this.bonus = 0;
        this.waitTime = 0;
    }

    /**
     * Return the total score of the whole simulation
     *
     * @return The total score
     */
    public int total() {
        return this.distanceScore + this.bonusScore;
    }

    /**
     * Increment the number of rides that are taken by one
     */
    public void incrementTaken() {
        this.taken++;
    }

    /**
     * Increment the bonus score by per-ride bonus for starting the ride on time
     *
     * @param bonus The bonus point for the ride that starts on time
     */
    public void incrementBonusScore(int bonus) {
        this.bonusScore += bonus;
    }

    /**
     * Increment the number of rides that starts on time by one
     */
    public void incrementBonus() {
        this.bonus++;
    }

    /**
     * Increment the total waiting time by the waiting time of that ride
     *
     * @param waitTime the total waiting time
     */
    public void incrementWaitTime(int waitTime) {
        this.waitTime += waitTime;
    }

    /**
     * Increment the distance score by o the distance between the start intersection and the finish intersection.
     *
     * @param distanceScore the distance score
     */
    public void incrementDistanceScore(int distanceScore) {
        this.distanceScore += distanceScore;
    }

    /**
     * Increment the number of ride that is late
     */
    public void incrementLate() {
        this.late++;
    }

}
